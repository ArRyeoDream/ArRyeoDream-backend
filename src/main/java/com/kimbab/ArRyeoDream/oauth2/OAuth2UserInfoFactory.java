package com.kimbab.ArRyeoDream.oauth2;

import com.kimbab.ArRyeoDream.enums.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider,Map<String, Object> attributes) {
        switch (authProvider) {
            case KAKAO: return new KakaoOAuth2User(attributes);

            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
