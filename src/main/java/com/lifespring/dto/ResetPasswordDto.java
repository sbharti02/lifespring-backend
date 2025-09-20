package com.lifespring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lifespring.validation.ValidEmailDomain;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResetPasswordDto {


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ValidEmailDomain
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String otp;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String otpMessage;


}
