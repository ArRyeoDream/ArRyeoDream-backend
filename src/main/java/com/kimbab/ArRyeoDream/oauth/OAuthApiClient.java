package com.kimbab.ArRyeoDream.oauth;

//api 요청 이후에 응답을 리턴해 주는 인터페이스
public interface OAuthApiClient {
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
