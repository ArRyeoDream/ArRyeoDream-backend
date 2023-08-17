package com.kimbab.ArRyeoDream.user.oauth;

import com.kimbab.ArRyeoDream.user.dto.Oauth2UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface OAuth2Service {
    public Oauth2UserDTO getOAuth2User(String oAuth2AccessToken);
}
