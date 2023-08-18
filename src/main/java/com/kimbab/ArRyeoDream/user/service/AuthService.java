package com.kimbab.ArRyeoDream.user.service;

import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.common.jwt.service.JwtService;
import com.kimbab.ArRyeoDream.oauth.OAuthApiClient;
import com.kimbab.ArRyeoDream.oauth.OAuthLoginParams;
import com.kimbab.ArRyeoDream.user.dto.JwtTokenDTO;
import com.kimbab.ArRyeoDream.user.dto.Oauth2UserDTO;
import com.kimbab.ArRyeoDream.user.dto.SignUpResponseDTO;
import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.oauth.OAuth2ServiceImpl;
import com.kimbab.ArRyeoDream.user.repository.UserRepository;
import com.kimbab.ArRyeoDream.user.service.refresh.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final OAuth2ServiceImpl oAuth2Service;
    private final UserRepository userRepository;
    private final UserService userSerivce;
    private final JwtService jwtService;
    private final RefreshService refreshService;
    private final OAuthApiClient client;


    @Transactional
    public SignUpResponseDTO login(OAuthLoginParams params){
        String accessToken = client.requestAccessToken(params);
        Oauth2UserDTO oauth2User = oAuth2Service.getOAuth2User(accessToken);
        String oauth2Id = findOrCreateUser(oauth2User);

        JwtTokenDTO jwtToken = jwtService.issue(accessToken);
        refreshService.storeRefresh(jwtToken, oauth2Id);

        return new SignUpResponseDTO(
                jwtToken,
                oauth2Id
        );
    }

    private String findOrCreateUser(Oauth2UserDTO oauth2UserDTO){
        return userRepository.findByOauth2Id(oauth2UserDTO.getOauth2Id())
                .map(User::getOauth2Id)
                .orElseGet(() -> newUser(oauth2UserDTO));
    }


    private String newUser(Oauth2UserDTO oauth2UserDTO){
        User user = User.builder()
                .oauth2Id(oauth2UserDTO.getOauth2Id())
                .email(oauth2UserDTO.getEmail())
                .nickname(oauth2UserDTO.getNickname())
                .level(0)
                .build();
        return userRepository.save(user).getOauth2Id();
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
