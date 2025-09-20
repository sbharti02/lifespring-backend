package com.lifespring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lifespring.validation.ValidEmailDomain;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID userId;
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Email cannot be null")
    @ValidEmailDomain
    private String email;
    @NotNull(message = "Password cannot be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String role;


}
