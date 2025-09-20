package com.lifespring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDto {
    // JWT token and message to send in response
    private String token;

    private String message;

    private String refreshToken;

}

