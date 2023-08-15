package com.kimbab.ArRyeoDream.oauth2.KaKao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kimbab.ArRyeoDream.oauth2.Service.OAuthInfoResponse;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KaKaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KaKaoAccount kaKaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KaKaoAccount{
        private KaKaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KaKaoProfile{
        private String nickname;
    }

    @Override
    public String getEmail(){
        return kaKaoAccount.email;
    }
    @Override
    public String getNickname(){return kaKaoAccount.profile.nickname;}

}
