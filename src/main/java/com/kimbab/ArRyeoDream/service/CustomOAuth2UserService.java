package com.kimbab.ArRyeoDream.service;

import com.kimbab.ArRyeoDream.domain.User;
import com.kimbab.ArRyeoDream.dto.UserReturnDto;
import com.kimbab.ArRyeoDream.enums.OAuthProvider;
import com.kimbab.ArRyeoDream.enums.Role;
import com.kimbab.ArRyeoDream.oauth2.OAuth2UserInfo;
import com.kimbab.ArRyeoDream.oauth2.OAuth2UserInfoFactory;
import com.kimbab.ArRyeoDream.oauth2.UserPrincipal;
import com.kimbab.ArRyeoDream.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    //oauth 인증 완료시 회원 정보 처리
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        // OAuth2UserRequest 객체 -> access token 값 포함
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
        System.out.println("oAuth2User.getName() = " + oAuth2User.getName());
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    protected OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        //OAuth2 로그인 플랫폼 구분
        OAuthProvider oAuthProvider = OAuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuthProvider, oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }

        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
        //이미 가입된 경우
        if (user != null) {
            System.out.println(" 가입 됐슴! ");
            if (!user.getOAuthProvider().equals(oAuthProvider)) {
                throw new RuntimeException("Email already signed up.");
            }
            user = updateUser(user, oAuth2UserInfo);
        }
        //가입되지 않은 경우
        else {
            System.out.println(" 가입 안됐슴! ");
            registerUser(oAuthProvider, oAuth2UserInfo);
        }
        System.out.println("oAuth2UserInfo = " + oAuth2UserInfo.getEmail());
        return UserPrincipal.create(user, oAuth2UserInfo.getAttributes());
    }

    private UserReturnDto registerUser(OAuthProvider OAuthProvider, OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .email(oAuth2UserInfo.getEmail())
                .name(oAuth2UserInfo.getName())
                .level(0)
                .oauth2Id(oAuth2UserInfo.getOAuth2Id())
                .OAuthProvider(OAuthProvider)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return new UserReturnDto(user);
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {

        return userRepository.save(user.update(oAuth2UserInfo));
    }
}
