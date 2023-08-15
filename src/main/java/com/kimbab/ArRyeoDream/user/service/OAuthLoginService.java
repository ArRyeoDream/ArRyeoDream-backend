package com.kimbab.ArRyeoDream.user.service;

import com.kimbab.ArRyeoDream.common.jwt.dto.AuthTokens;
import com.kimbab.ArRyeoDream.common.jwt.service.AuthTokensGenerator;
import com.kimbab.ArRyeoDream.oauth.OAuthApiClient;
import com.kimbab.ArRyeoDream.oauth.OAuthInfoResponse;
import com.kimbab.ArRyeoDream.oauth.OAuthLoginParams;
import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    // sns(OAtuh인증이 된 플랫폼에서 로그인 및 인증
    // email정보로 사용자 확인 (사용자 없으면 회원가입 처리)
    // 인증이 되었으면 AccessToken 생성 후 내려주기
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final OAuthApiClient client;

    public AuthTokens login(OAuthLoginParams params){
        String accessToken = client.requestAccessToken(params);
        OAuthInfoResponse oAuthInfoResponse = client.requestOauthInfo(accessToken);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse){
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse){
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .level(0)
                .build();
        return userRepository.save(user).getId();
    }
}

