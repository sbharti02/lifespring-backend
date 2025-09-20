package com.lifespring.service.impl;

import com.lifespring.config.ModelMapperConfig;
import com.lifespring.dto.JwtResponseDto;
import com.lifespring.dto.RefreshTokenDto;
import com.lifespring.exception.ResourceNotFoundException;
import com.lifespring.exception.TokenExpiredException;
import com.lifespring.model.RefreshToken;
import com.lifespring.model.User;
import com.lifespring.repository.RefreshTokenRepository;
import com.lifespring.repository.UserRepository;
import com.lifespring.security.JwtService;
import com.lifespring.service.RefreshTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ModelMapper mapper;

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshExpiration;

    @Override
    public RefreshTokenDto createRefreshToken(UUID userId) {
        // get user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user found with the given id "));



        // generating the refresh token !!
        RefreshToken token =  new RefreshToken();

        token.setRefreshTokenId(UUID.randomUUID());
        token.setRefreshToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(refreshExpiration));
        token.setUser(user);


        //save the refresh token to the database
        RefreshToken savedRefreshToken = refreshTokenRepository.save(token);



        // setting the value in the jwt response


        return mapper.map(savedRefreshToken,RefreshTokenDto.class);



    }

    @Override
    public JwtResponseDto  validateRefreshToken(String  refreshToken) {

        // Find the refreshToken in the database

        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new ResourceNotFoundException("Invalid token please provide valid refresh Token !!"));
        if(token.getExpiryDate().isBefore(Instant.now()) ){
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException("Refresh token is expired please login again !!");
        }

        // if token is valid -> generate new jwt token -> otherwise return error message
        String newJwtToken = jwtService.generateToken(token.getUser().getUsername());

       return  JwtResponseDto.builder()
               .message("Generating new Jwt token ")
               .refreshToken(token.getRefreshToken())
               .token(newJwtToken)
               .build();
    }



    @Override
    public Optional<RefreshTokenDto> findByUser(User user) {
     return  refreshTokenRepository.findByUser(user).map(token ->mapper.map(token,RefreshTokenDto.class));
    }

    @Override
    public void deleteRefreshToken(RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = mapper.map(refreshTokenDto, RefreshToken.class);

        refreshTokenRepository.delete(refreshToken);
    }


}
