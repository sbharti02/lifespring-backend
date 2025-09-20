package com.lifespring.security;

import com.lifespring.exception.ResourceNotFoundException;
import com.lifespring.model.User;
import com.lifespring.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. we need to get the token from the request header
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response); // let spring handle it and return so rest of code is not executed
            return;
        }

        //2. Extreact the token
        jwtToken = authHeader.substring(7); // remove the "Bearer " part and we get the token

        //3 Extract the username from the token
        username = jwtService.extractUsername(jwtToken);

        //4. Check if the username is not null and the user is not authenticated yet

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User authUserDetails = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(" No user found with the given name "));
            if (jwtService.validateToken(jwtToken, authUserDetails.getUsername())){
                UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(authUserDetails.getUsername())
                        .password(authUserDetails.getPassword())        // set password from your DB user
                        .roles(authUserDetails.getRole())                                  // assign roles, can fetch dynamically from DB
                        .build();

               // 5 . set the authentication in the context
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }

        //6. Continue the filter chain , if everything is fine or let spring handle the exception
        filterChain.doFilter(request,response); // let spring handle it
    }
}
