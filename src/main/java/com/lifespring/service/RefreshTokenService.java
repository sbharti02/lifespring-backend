package com.lifespring.service;

import com.lifespring.dto.JwtResponseDto;
import com.lifespring.dto.RefreshTokenDto;
import com.lifespring.model.RefreshToken;
import com.lifespring.model.User;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    RefreshTokenDto createRefreshToken(UUID userId);

   JwtResponseDto validateRefreshToken(String refreshToken);


    Optional<RefreshTokenDto> findByUser(User user);

    void deleteRefreshToken(RefreshTokenDto refreshTokenDto);
}
