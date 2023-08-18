package com.kimbab.ArRyeoDream.common.jwt.util;

import com.kimbab.ArRyeoDream.common.jwt.user.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtResolver {
//    @Value("${jwt.secret-key}")
    private String SECRET_KEY = "88293b6cd1b7b3b6b4323a1fb42e3c1ebf17776be2eab4f0121a77cc646935496fe3cd6b26b96f899b12388c26c7558f6837802293543512dc61426cb68553cd";
    @Value("Authorization")
    private String ACCESS_TOKEN_HEADER;
    @Value("RefreshToken")
    private String REFRESH_TOKEN_HEADER;

    private final UserDetailsServiceImpl userDetailsService;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveAccessToken(HttpServletRequest request){
        return request.getHeader(ACCESS_TOKEN_HEADER).replace("Bearer", "").trim();
    }

    public String resolveRefreshToken(HttpServletRequest request){
        return request.getHeader(REFRESH_TOKEN_HEADER);
    }

    public Jws<Claims> parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public Boolean isExpired(String token, Date date){
        try{
            Jws<Claims> claims = parseToken(token);
            return !claims.getBody().getExpiration().before(date);
        } catch(Exception e){
            return false;
        }
    }

    private String getUserPk(String token){
        try{
            return parseToken(token).getBody().getSubject();
        } catch (ExpiredJwtException e){
            return e.getClaims().getSubject();
        }
    }
}
