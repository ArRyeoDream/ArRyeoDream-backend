package com.kimbab.ArRyeoDream.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oauth2UserDTO {
    private String oauth2Id;
    private String nickname;
    private String email;
}
