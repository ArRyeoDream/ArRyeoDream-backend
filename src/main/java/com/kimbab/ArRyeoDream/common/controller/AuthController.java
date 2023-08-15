package com.kimbab.ArRyeoDream.common.controller;

import com.kimbab.ArRyeoDream.common.jwt.dto.AuthTokens;
import com.kimbab.ArRyeoDream.oauth.LoginParams;
import com.kimbab.ArRyeoDream.user.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody LoginParams params){
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}
