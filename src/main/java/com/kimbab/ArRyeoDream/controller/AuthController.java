package com.kimbab.ArRyeoDream.controller;

import com.kimbab.ArRyeoDream.jwt.dto.AuthTokens;
import com.kimbab.ArRyeoDream.oauth2.KaKao.KaKaoLoginParams;
import com.kimbab.ArRyeoDream.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKaKao(@RequestBody KaKaoLoginParams params){
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}
