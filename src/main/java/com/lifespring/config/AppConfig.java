package com.lifespring.config;

import com.lifespring.dto.JwtResponseDto;
import com.lifespring.model.RefreshToken;
import com.lifespring.security.oauth.GoogleOAuth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public JwtResponseDto jwtResponseDto(){
        return new JwtResponseDto();
    }

    @Bean
    public RefreshToken refreshToken(){
        return new RefreshToken();
    }


}
