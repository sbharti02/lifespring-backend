package com.lifespring.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifespring.dto.GoogleLoginResponseDto;
import com.lifespring.dto.RefreshTokenDto;
import com.lifespring.dto.UserDto;
import com.lifespring.security.JwtService;
import com.lifespring.service.RefreshTokenService;
import com.lifespring.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;


    public GoogleOAuth2SuccessHandler( @Lazy UserService userService) {
        this.userService = userService;

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 1 .get the object of the user that are coming  in the authentication
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 2. fetch the details you want
        String email = oAuth2User.getAttribute("email");

        String username = oAuth2User.getAttribute("name");


        // Agr -> user login via google then it  is authenticated -> Toh save the user db

        UserDto userDto =  new UserDto();
        userDto.setUsername(username);
        userDto.setEmail(email);
        userDto.setPassword("google");

        // save the user on the database

        UserDto registerUser = userService.registerUser(userDto);


        GoogleLoginResponseDto googleLoginResponseDto = new GoogleLoginResponseDto();

        googleLoginResponseDto.setUserDto(userDto);


        // write the response that we want

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), googleLoginResponseDto);
    }
}

