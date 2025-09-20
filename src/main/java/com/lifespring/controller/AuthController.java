package com.lifespring.controller;

import com.lifespring.dto.*;
import com.lifespring.exception.InvalidCredentialsException;
import com.lifespring.model.User;
import com.lifespring.service.OTPService;
import com.lifespring.service.RefreshTokenService;
import com.lifespring.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@Log4j2
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private OTPService otpService;

    // Register user controller
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUserController(@RequestBody @Valid UserDto userDto) {
        UserDto registerUser = userService.registerUser(userDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    // Login user controller
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> loginController(@RequestBody LoginRequestDto loginRequestDto){
        JwtResponseDto jwtResponseDto = userService.loginUser(loginRequestDto);
        return new ResponseEntity<>(jwtResponseDto,HttpStatus.OK);
    }

    // RefreshToken controller
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refreshTokenValidateController(@RequestParam String authHeader){

        try{
            if(authHeader == null ){
                throw new InvalidCredentialsException("Header is null or it may not start with the barer!");
            }
            JwtResponseDto jwtResponseDto = refreshTokenService.validateRefreshToken(authHeader);
            return new ResponseEntity<>(jwtResponseDto ,HttpStatus.ACCEPTED);


        }catch (Exception exception){
            log.info("Exception message is here => " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(JwtResponseDto.builder()
                            .message(exception.getMessage())
                            .build());
        }

    }

    // Reset the password if user forget it

    @PostMapping("/reset")
    public ResponseEntity<ResetPasswordDto> resetPasswordController(@RequestBody ResetPasswordDto resetPasswordDto){
        ResetPasswordDto resetPasswordDto1 = otpService.generateOtp(resetPasswordDto);
        return new ResponseEntity<>(resetPasswordDto1,HttpStatus.OK);
    }

// We need to verify the otp

    @PostMapping("/verify-otp")
    public  ResponseEntity<OtpDto> verifyOTpController(@RequestBody OtpDto otpDto , @RequestParam("email") String userEmail){
        OtpDto verifyOtp = otpService.verifyOtp(otpDto, userEmail);

        return new ResponseEntity<>(otpDto,HttpStatus.OK);
    }

}
