package com.kimbab.ArRyeoDream.domain;

import com.kimbab.ArRyeoDream.enums.AuthProvider;
import com.kimbab.ArRyeoDream.enums.Role;
import com.kimbab.ArRyeoDream.oauth2.OAuth2UserInfo;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name="users")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauth2Id;

    private String email;
    private String name;
    private Integer level;

    // 관리자, 사용자 정도로만 나눴습니다.
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    public User update(OAuth2UserInfo oAuth2UserInfo) {
        this.name = oAuth2UserInfo.getName();
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();

        return  this;
    }
}
