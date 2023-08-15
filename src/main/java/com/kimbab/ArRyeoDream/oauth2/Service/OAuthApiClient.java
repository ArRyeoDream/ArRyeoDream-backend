package com.kimbab.ArRyeoDream.oauth2.Service;

public interface OAuthApiClient {

    // auth code를 기반으로 api를 요청해서 accessToken을 발급받는 함수
    String requestAccessToken(OAuthLoginParams params);

    // AccessToken을 넣어서 nickname, email 등이 포함된 그런 프로필 정보를 획들하는 함수
    OAuthInfoResponse requestOauthInfo(String accessToken);

}
