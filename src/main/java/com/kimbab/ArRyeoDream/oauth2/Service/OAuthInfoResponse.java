package com.kimbab.ArRyeoDream.oauth2.Service;

// api를 요청했을 때 회원 정보를 응답
public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();

}
