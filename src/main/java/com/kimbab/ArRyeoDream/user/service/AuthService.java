package com.kimbab.ArRyeoDream.user.service;

import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.common.jwt.service.JwtService;
import com.kimbab.ArRyeoDream.user.dto.JwtTokenDTO;
import com.kimbab.ArRyeoDream.user.dto.Oauth2UserDTO;
import com.kimbab.ArRyeoDream.user.dto.SignInRequestDTO;
import com.kimbab.ArRyeoDream.user.dto.SignUpResponseDTO;
import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.oauth.OAuth2ServiceImpl;
import com.kimbab.ArRyeoDream.user.service.refresh.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final OAuth2ServiceImpl oAuth2Service;
    private final UserService userSerivce;
    private final JwtService jwtService;
    private final RefreshService refreshService;

    @Transactional
    public SignUpResponseDTO signUp(SignInRequestDTO signInRequestDTO){
        Oauth2UserDTO oAuth2User = oAuth2Service.getOAuth2User(signInRequestDTO.getAuthorizationCode());

        if(userSerivce.existsByOauth2Id(oAuth2User.getOauth2Id())){
            throw new BusinessException(ErrorCode.USER_DUPLICATED);
        }

        User savedUser = userSerivce.saveUser(
                User.builder()
                .oauth2Id(oAuth2User.getOauth2Id())
                .email(oAuth2User.getEmail())
                .nickname(oAuth2User.getNickname())
                .level(0)
                .build()
        );

        JwtTokenDTO jwtToken = jwtService.issue(signInRequestDTO.getAuthorizationCode());
        refreshService.storeRefresh(jwtToken, oAuth2User.getOauth2Id());

        return new SignUpResponseDTO(
                jwtToken,
                savedUser.getOauth2Id()
        );
    }

    @Transactional
    public JwtTokenDTO signin(SignInRequestDTO signInRequestDTO){
        JwtTokenDTO jwtToken = jwtService.issue(signInRequestDTO.getAuthorizationCode());

        Oauth2UserDTO oAuth2User = oAuth2Service.getOAuth2User(signInRequestDTO.getAuthorizationCode());

        refreshService.storeRefresh(jwtToken, oAuth2User.getOauth2Id());
        return jwtToken;
    }

    @Transactional
    public JwtTokenDTO refresh(String refreshToken){
        String oAuth2Id = refreshService.getRefresh(refreshToken);
        if(oAuth2Id == null){
            throw new BusinessException(ErrorCode.INVALID_JWT);
        }
        User user = userSerivce.findByOauth2Id(oAuth2Id);

        JwtTokenDTO jwtToken = jwtService.refresh(refreshToken, user.getOauth2Id());
        refreshService.storeRefresh(jwtToken, oAuth2Id);
        return jwtToken;
    }

    @Transactional
    public void logout(String refreshToken, String accessToken){
        refreshService.deleteRefresh(refreshToken);
    }

}
