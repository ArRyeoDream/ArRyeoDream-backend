package com.kimbab.ArRyeoDream.common.jwt.util;

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
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token-expiry}")
    private Long accessTokenValidTime;
    @Value("${jwt.refresh-token-expiry}")
    private Long refreshTokenValidTime;

    private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    public String createAccessToken(String oAuth2Id){
        Claims claims = Jwts.claims().setSubject(oAuth2Id);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
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
