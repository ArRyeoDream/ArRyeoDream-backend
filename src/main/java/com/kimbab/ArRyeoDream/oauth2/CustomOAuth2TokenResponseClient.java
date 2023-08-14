package com.kimbab.ArRyeoDream.oauth2;

import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.AbstractOAuth2AuthorizationGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;


import static io.jsonwebtoken.Header.CONTENT_TYPE;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;

public class CustomOAuth2TokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>{

    private static final String INVALID_TOKEN_RESPONSE_ERROR_CODE = "invalid_token_response";

    private RestOperations restOperations;


    public CustomOAuth2TokenResponseClient() {
        RestTemplate restTemplate = new RestTemplate(
                Arrays.asList(new FormHttpMessageConverter(), new OAuth2AccessTokenResponseHttpMessageConverter()));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }



    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
        HttpHeaders headers = generateHeaders();
        MultiValueMap<String, String> param = generateParam(clientRegistration, authorizationGrantRequest);
        // yml에 설정되어있는 기본 uri 가져옴
        URI uri = getUri(authorizationGrantRequest);
        RequestEntity<?> requestEntity = new RequestEntity<>(param, headers, HttpMethod.POST, uri);
        ResponseEntity<OAuth2AccessTokenResponse> response = getResponse(requestEntity);
        return response.getBody();
    }

    private URI getUri(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        return UriComponentsBuilder
                .fromUriString(authorizationGrantRequest.getClientRegistration().getProviderDetails().getTokenUri())
                .build().toUri();
    }

    private MultiValueMap<String, String> generateParam(
            ClientRegistration clientRegistration,
            OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest
    ) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(GRANT_TYPE, clientRegistration.getAuthorizationGrantType().getValue());
        params.add(CLIENT_ID, clientRegistration.getClientId());
        params.add(CLIENT_SECRET, clientRegistration.getClientSecret());
        params.add(CODE, authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        return params;
    }

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, "application/x-www-form-urlencoded");
        return headers;
    }

    private ResponseEntity<OAuth2AccessTokenResponse> getResponse(RequestEntity<?> request) {
        try {
            return this.restOperations.exchange(request, OAuth2AccessTokenResponse.class);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_TOKEN_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: "
                            + ex.getMessage(),
                    null);
            throw new OAuth2AuthorizationException(oauth2Error, ex);
        }
    }


}
