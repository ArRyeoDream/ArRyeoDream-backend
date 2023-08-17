package com.kimbab.ArRyeoDream.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenDTO {
    private String accessToken;
    private String refreshToken;
}
