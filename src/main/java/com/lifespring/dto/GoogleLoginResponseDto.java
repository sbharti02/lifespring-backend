package com.lifespring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleLoginResponseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto userDto;


}
