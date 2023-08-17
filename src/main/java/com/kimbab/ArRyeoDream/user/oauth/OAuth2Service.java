package com.kimbab.ArRyeoDream.user.oauth;

import com.kimbab.ArRyeoDream.user.dto.Oauth2UserDTO;

public interface OAuth2Service {
    Oauth2UserDTO getOAuth2User(String oAuth2AccessToken);
}
