package com.kimbab.ArRyeoDream.oauth2.KaKao;

import com.kimbab.ArRyeoDream.oauth2.Service.OAuthApiClient;
import com.kimbab.ArRyeoDream.oauth2.Service.OAuthInfoResponse;
import com.kimbab.ArRyeoDream.oauth2.Service.OAuthLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KaKaoApiClient implements OAuthApiClient {
    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.client-secret")
    private String clientSecret;

    private RestTemplate restTemplate;

    @Override
    public String requestAccessToken(OAuthLoginParams params){
        String url = authUrl + "/oauth/token";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type",GRANT_TYPE);
        body.add("client_id",clientId);
        body.add("client_secret",clientSecret);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KaKaoTokens response = restTemplate.postForObject(url,request,KaKaoTokens.class);

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken){
        String url = apiUrl + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization","Bearer"+ accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys","[\"kakao_account.email\",\"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KaKaoInfoResponse response = restTemplate.postForObject(url, request, KaKaoInfoResponse.class);
        assert response != null;
        return response;
    }
}
