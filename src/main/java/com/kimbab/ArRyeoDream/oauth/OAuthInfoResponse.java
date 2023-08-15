package com.kimbab.ArRyeoDream.oauth;

//api를 요청했을때 회원 정보를 응답해주는 인터페이스
public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
}
