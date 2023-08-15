package com.kimbab.ArRyeoDream.oauth2.Service;

import org.springframework.util.MultiValueMap;

//api 요청에 필요한 파라미터 정보를 담고 있는 인터페이스
public interface OAuthLoginParams {
    MultiValueMap<String, String> makeBody();
}
