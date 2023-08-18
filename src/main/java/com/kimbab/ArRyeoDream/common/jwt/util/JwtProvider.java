package com.kimbab.ArRyeoDream.common.jwt.util;

import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtProvider {
//    @Value("${jwt.secret-key}")
    private String secretKey = "88293b6cd1b7b3b6b4323a1fb42e3c1ebf17776be2eab4f0121a77cc646935496fe3cd6b26b96f899b12388c26c7558f6837802293543512dc61426cb68553cd";
    @Value("${jwt.access-token-expiry}")
    private Long accessTokenValidTime;
    @Value("${jwt.refresh-token-expiry}")
    private Long refreshTokenValidTime;
    private final UserService userService;

    private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    public String createAccessToken(String oAuth2Id){
        User user = userService.findByOauth2Id(oAuth2Id);
        if(user == null){
            return null;
        }
        Claims claims = Jwts.claims().setSubject(oAuth2Id);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .claim("nickname", user.getNickname())
                .claim("email", user.getEmail())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(key)
                .compact();
    }
}
