package com.lifespring.service;

import com.lifespring.dto.JwtResponseDto;
import com.lifespring.dto.LoginRequestDto;
import com.lifespring.dto.UserDto;
import com.lifespring.model.RefreshToken;
import com.lifespring.model.User;

import java.util.Optional;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    JwtResponseDto loginUser(LoginRequestDto loginRequestDto);



}
