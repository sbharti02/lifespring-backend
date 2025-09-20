package com.lifespring.service;

import com.lifespring.dto.OtpDto;
import com.lifespring.dto.ResetPasswordDto;

public interface OTPService {

    ResetPasswordDto generateOtp(ResetPasswordDto resetPasswordDto);

    OtpDto verifyOtp(OtpDto otpDto,String userEmail);

}
