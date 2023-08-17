package com.kimbab.ArRyeoDream.common.jwt.filter;

import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.common.jwt.util.JwtResolver;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtResolver jwtResolver;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
        try{
            String accessToken = jwtResolver.resolveAccessToken((HttpServletRequest)request);
            jwtResolver.parseToken(accessToken);
            setAuthentication(accessToken);
        } catch (ExpiredJwtException e){
            throw new BusinessException(ErrorCode.EXPIRED_JWT);
        } catch (Exception e){
            throw new BusinessException(ErrorCode.INVALID_JWT);
        }
    }

    private void setAuthentication(String accessToken){
        Authentication authentication = jwtResolver.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
