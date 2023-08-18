package com.kimbab.ArRyeoDream.user.entity;

import com.kimbab.ArRyeoDream.common.entity.BaseBoardEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor
public class RefreshToken extends BaseBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    private String refreshToken;

    private String oauth2Id;

    @Builder
    public RefreshToken(String refreshToken, String oauth2Id){
        this.refreshToken = refreshToken;
        this.oauth2Id = oauth2Id;
    }
}
