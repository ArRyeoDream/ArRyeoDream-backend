package com.kimbab.ArRyeoDream.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoResponse implements OAuthInfoResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile{
        private String nickname;
    }

    @Override
    public String getEmail(){
        return kakaoAccount.email;
    }

    @Override
    public String getNickname(){
        return kakaoAccount.profile.nickname;
    }

    @Override
    public String getOauth2Id(){
        return id.toString();
    }
}
