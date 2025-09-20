package com.lifespring.service.impl;

import com.lifespring.config.AppConstant;
import com.lifespring.dto.OtpDto;
import com.lifespring.dto.ResetPasswordDto;
import com.lifespring.exception.InvalidCredentialsException;
import com.lifespring.exception.InvalidUserDetailException;
import com.lifespring.model.OTP;
import com.lifespring.model.User;
import com.lifespring.repository.OTPRepository;
import com.lifespring.repository.UserRepository;
import com.lifespring.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class OTPServiceImpl implements OTPService {

   private UserRepository userRepository;

    private OTPRepository otpRepository;

    private EmailServiceImpl emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public OTPServiceImpl(UserRepository userRepository, OTPRepository otpRepository , EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }


    @Override
    public ResetPasswordDto generateOtp(ResetPasswordDto resetPasswordDto) {
        OTP otp =  new OTP();
        String userEmail = resetPasswordDto.getEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new InvalidCredentialsException("Please enter the valid email ! no user exits with that email"));

        // if user is present then we need to generate the otp
        int randomOtp = 100000 + new Random().nextInt(900000);//generate otp up to 6 digit
        otp.setOtpId(UUID.randomUUID());
        otp.setUserId(user.getUserId());
        otp.setOtpCode(String.valueOf(randomOtp));
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiredAt(LocalDateTime.now().plusMinutes(3));


        OTP saved = otpRepository.save(otp);


        // now we need to return the reset password dto
        resetPasswordDto.setOtpMessage("Please enter your otp ");



        return resetPasswordDto;
    }

   // verify otp
    @Override
    public OtpDto verifyOtp(OtpDto otpDto,String userEmail) {

       User user =   userRepository.findByEmail(userEmail).orElseThrow(()-> new InvalidCredentialsException("No user found with the given email please try again !!!"));
        OTP otp = otpRepository.findByUserId(user.getUserId()).orElseThrow(() -> new InvalidUserDetailException("No user found with the given email !!"));

        // check whether the otp is expired or not

        if(otp.getExpiredAt().isAfter(LocalDateTime.now()) && otp.getOtpCode().equals(otpDto.getOtp())){
            // This means the otp is valid
            // so we will change the password or set new password

            user.setPassword(passwordEncoder.encode(otpDto.getNewPassword()));

            // save the new password
            userRepository.save(user);

            // delete the otp repo
            otpRepository.delete(otp);


            // set the success method to the user

            otpDto.setMessage("Done , Password changed !! successfully !!");

            return otpDto;

        }

        otpDto.setMessage("Not a valid OTP please try again !");

        return otpDto;
    }





}
