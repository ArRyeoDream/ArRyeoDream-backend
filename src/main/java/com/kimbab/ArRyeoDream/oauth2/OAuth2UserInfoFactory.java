package com.kimbab.ArRyeoDream.oauth2;

import com.kimbab.ArRyeoDream.enums.OAuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(OAuthProvider OAuthProvider, Map<String, Object> attributes) {
        switch (OAuthProvider) {
            case KAKAO: return new KakaoOAuth2User(attributes);

            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
