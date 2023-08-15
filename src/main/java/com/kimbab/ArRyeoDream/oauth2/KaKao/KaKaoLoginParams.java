package com.kimbab.ArRyeoDream.oauth2.KaKao;

import com.kimbab.ArRyeoDream.oauth2.Service.OAuthLoginParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class KaKaoLoginParams implements OAuthLoginParams {
    private String authorizationCode;

    @Override
    public MultiValueMap<String, String> makeBody(){
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("code",authorizationCode);
        return body;
    }


}
