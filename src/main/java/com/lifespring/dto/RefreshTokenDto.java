package com.lifespring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID refreshTokenId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String refreshToken;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant expiryDate;
}
