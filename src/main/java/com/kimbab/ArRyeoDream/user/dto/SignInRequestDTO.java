package com.kimbab.ArRyeoDream.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInRequestDTO {
    @JsonProperty("authorizationCode")
    private String authorizationCode;
}
