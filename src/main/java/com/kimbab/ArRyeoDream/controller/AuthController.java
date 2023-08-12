package com.kimbab.ArRyeoDream.controller;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    //토큰 발급되는지 확인하는 테스트용 컨트롤러
    @GetMapping(value = "token")
    public String token(@RequestParam String token, @RequestParam String error){
        if(StringUtils.isNotBlank(error)){
            return error;
        } else{
            return token;
        }
    }
}
