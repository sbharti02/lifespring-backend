package com.lifespring.security;

import com.lifespring.config.AppConstant;
import com.lifespring.security.oauth.GoogleOAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //security filter chain bean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;

    SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint){
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

//        http
//               .csrf(CsrfConfigurer :: disable)
//               .authorizeHttpRequests(auth->{
//                   auth.requestMatchers("/api/v1/auth/**").permitAll()
//                           .requestMatchers("/api/v1/patient/create","/api/v1/patient/update", "/api/v1/patient/delete","/api/v1/patient/byId/{id}").hasRole(AppConstant.PATIENT)
//                           .anyRequest().authenticated();
//               } )
//               .exceptionHandling(ex->{
//                   ex.authenticationEntryPoint(jwtAuthenticationEntryPoint);
//               })
//               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2Login(oauth-> oauth.defaultSuccessUrl("/google" , true))
//               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth.successHandler(googleOAuth2SuccessHandler));
       return http.build();
    }

    //password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
