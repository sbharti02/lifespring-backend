package com.lifespring.service.impl;

import com.lifespring.config.AppConstant;
import com.lifespring.dto.JwtResponseDto;
import com.lifespring.dto.LoginRequestDto;
import com.lifespring.dto.RefreshTokenDto;
import com.lifespring.dto.UserDto;
import com.lifespring.exception.InvalidCredentialsException;
import com.lifespring.exception.InvalidUserDetailException;
import com.lifespring.exception.ResourceNotFoundException;
import com.lifespring.exception.UserAlreadyExistsException;
import com.lifespring.model.RefreshToken;
import com.lifespring.model.User;
import com.lifespring.repository.UserRepository;
import com.lifespring.security.JwtService;
import com.lifespring.service.RefreshTokenService;
import com.lifespring.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private ModelMapper mapper;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;



    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    UserServiceImpl(UserRepository userRepository ,ModelMapper mapper, PasswordEncoder passwordEncoder , JwtService jwtService ){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;


    }
    @Override
    public UserDto registerUser(UserDto userDto) {
        //Now lets start the implementation of this method

        // first -> check if the user is already exist in the database by username or email
          String username = userDto.getUsername();
            String email = userDto.getEmail();

          if(userRepository.findByUsernameOrEmail(username,email).isPresent()){
              // user is already exist in the database
              throw new UserAlreadyExistsException("User Already Exists with this username or email , please try with different one");

          }


        //second -> every user that register is a normal user means he/she  has a role of Patient

        userDto.setRole(AppConstant.PATIENT);
        userDto.setUserId(UUID.randomUUID());

        // setting the password in encrypted format
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //convert userDto to user entity
        User user = mapper.map(userDto, User.class);

        //save the user entity to database
        userRepository.save(user);

        //convert the saved user to dto
        return mapper.map(user, UserDto.class);

    }

    @Override
    public JwtResponseDto loginUser(LoginRequestDto loginRequestDto) {

        //1 . validate the user

        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> new ResourceNotFoundException("No user found with the given username !!"));

        // 2. validate the password
        if(!passwordEncoder.matches(loginRequestDto.getPassword() , user.getPassword())){
            throw new InvalidCredentialsException("Please enter valid password & username !!");
        }

        // 3. generate the jwt token
        String jwtToken = jwtService.generateToken(loginRequestDto.getUsername());

        //4 . generate the refresh token

        String refreshToken ;
        Optional<RefreshTokenDto> existingTokenOpt = refreshTokenService.findByUser(user);


        if(existingTokenOpt.isPresent()){
            if(existingTokenOpt.get().getExpiryDate().isAfter(Instant.now())){
                refreshToken = existingTokenOpt.get().getRefreshToken();
            }else {
                // old -> refresh token is deleted
                refreshTokenService.deleteRefreshToken(existingTokenOpt.get());

                // if token is expired we are creating new token
                RefreshTokenDto newRefreshToken = refreshTokenService.createRefreshToken(user.getUserId());
                refreshToken = newRefreshToken.getRefreshToken();
            }
        }else{
            //create new RefreshToken
            RefreshTokenDto newRefreshToken = refreshTokenService.createRefreshToken(user.getUserId());
            refreshToken = newRefreshToken.getRefreshToken();
        }




//        if(existingTokenOpt.isPresent() && existingTokenOpt.get().getExpiryDate().isAfter(Instant.now())){
//          // means the token is not expired
//            refreshToken = existingTokenOpt.get().getRefreshToken();
//        }else{
//            //create new RefreshToken
//            RefreshTokenDto newRefreshToken = refreshTokenService.createRefreshToken(user.getUserId());
//            refreshToken = newRefreshToken.getRefreshToken();
//        }

        //5. return the response

        return JwtResponseDto.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .message("Login Success full  !!")
                .build();
    }

    // find user by email




}
