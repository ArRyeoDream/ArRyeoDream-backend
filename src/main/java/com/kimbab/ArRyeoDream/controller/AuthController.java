package com.kimbab.ArRyeoDream.controller;

import com.kimbab.ArRyeoDream.common.jwt.util.JwtResolver;
import com.kimbab.ArRyeoDream.oauth.LoginParams;
import com.kimbab.ArRyeoDream.user.dto.JwtTokenDTO;
import com.kimbab.ArRyeoDream.user.dto.SignUpResponseDTO;
import com.kimbab.ArRyeoDream.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtResolver jwtResolver;

    @PostMapping("/kakao")
    public ResponseEntity<SignUpResponseDTO> loginKakao(@RequestBody LoginParams params){
        return ResponseEntity.ok(authService.login(params));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtTokenDTO> refresh(HttpServletRequest request){
        String refreshToken = jwtResolver.resolveRefreshToken(request);
        return ResponseEntity.ok().body(authService.refresh(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        String refreshToken = jwtResolver.resolveRefreshToken(request);
        String accessToken = jwtResolver.resolveAccessToken(request);
        authService.logout(refreshToken, accessToken);
        return ResponseEntity.ok().body(null);
    }
}
