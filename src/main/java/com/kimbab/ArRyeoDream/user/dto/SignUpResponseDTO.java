package com.kimbab.ArRyeoDream.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponseDTO {
    private JwtTokenDTO jwt;
    private String oauth2Id;
}
