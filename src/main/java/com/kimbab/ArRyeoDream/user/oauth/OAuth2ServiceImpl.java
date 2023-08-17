package com.kimbab.ArRyeoDream.user.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.user.dto.Oauth2UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class OAuth2ServiceImpl implements OAuth2Service{
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    @Override
    public Oauth2UserDTO getOAuth2User(String oAuth2AccessToken){
        JsonNode userJson = getProfileInfoFromProvider(oAuth2AccessToken);
        return buildOAuth2User(userJson);
    }

    private JsonNode getProfileInfoFromProvider(String oAuth2AccessToken){
        try{
            ResponseEntity<String> response =
                restTemplate.postForEntity(
                KAKAO_USER_INFO_URI,
                buildRequest(oAuth2AccessToken),
                String.class
            );

            return objectMapper.readTree(response.getBody());
        } catch (HttpClientErrorException e){
            throw new BusinessException(ErrorCode.OAUTH2_FAIL_EXCEPTION);
        } catch (JsonProcessingException e){
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private HttpEntity<?> buildRequest(String oAuth2AccessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(oAuth2AccessToken);
        return new HttpEntity(null, headers);
    }

    private Oauth2UserDTO buildOAuth2User(JsonNode jsonNode){
        JsonNode kakao_account = jsonNode.get("kakao_account");
        String oAuth2Id = jsonNode.get("id").asText();
        String nickname = kakao_account
                .get("profile")
                .get("nickname")
                .asText();
        String email = kakao_account.get("email").asText();

        return new Oauth2UserDTO(
                oAuth2Id,
                nickname,
                email
        );
    }
}
