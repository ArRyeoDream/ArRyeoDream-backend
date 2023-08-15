package com.kimbab.ArRyeoDream.service;

import com.kimbab.ArRyeoDream.domain.User;
import com.kimbab.ArRyeoDream.jwt.dto.AuthTokens;
import com.kimbab.ArRyeoDream.jwt.service.AuthTokenGenerator;
import com.kimbab.ArRyeoDream.oauth2.Service.OAuthApiClient;
import com.kimbab.ArRyeoDream.oauth2.Service.OAuthInfoResponse;
import com.kimbab.ArRyeoDream.oauth2.Service.OAuthLoginParams;
import com.kimbab.ArRyeoDream.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    // sns( oauth 인증이 된 플랫폼에서 로그인 및 인증
    // email 정보로 사용자 확인 ( 사용자 없으면 회원가입 처리)
    // 인증이 되었으면 AccessToken 생성 후 내려주기
    private final UserRepository userRepository;
    private final AuthTokenGenerator authTokenGenerator;
    private final OAuthApiClient client;

    public AuthTokens login(OAuthLoginParams params){
        String accessToken = client.requestAccessToken(params);
        OAuthInfoResponse oAuthInfoResponse = client.requestOauthInfo(accessToken)
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokenGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse){
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(()-> newUser(oAuthInfoResponse));
    }

    private Long newUser(OAuthInfoResponse oAuthInfoResponse){
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickname())
                .level(0)
                .build();
        return userRepository.save(user).getId();
    }

}
