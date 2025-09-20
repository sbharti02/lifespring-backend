package com.lifespring.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Here we are sending the 401 unauthorized response to the client if the user is not authenticated
    // This class is used to handle the exception when the user is not authenticated
    // and tries to access a protected resource
    // We can customize the response here
    // For example, we can send a JSON response with a custom message

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
       response.sendError(HttpServletResponse.SC_UNAUTHORIZED , "unauthorized"+ authException.getMessage());
    }
}
