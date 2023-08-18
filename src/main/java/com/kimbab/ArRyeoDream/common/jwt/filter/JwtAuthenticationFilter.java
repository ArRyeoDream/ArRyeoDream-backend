package com.kimbab.ArRyeoDream.common.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.common.error.ErrorResponse;
import com.kimbab.ArRyeoDream.common.jwt.util.JwtResolver;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtResolver jwtResolver;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            String accessToken = jwtResolver.resolveAccessToken((HttpServletRequest)request);
            jwtResolver.parseToken(accessToken);
            setAuthentication(accessToken);
        } catch (ExpiredJwtException e){
            sendErrorResponse((HttpServletResponse) response, ErrorCode.EXPIRED_JWT);
        } catch (Exception e){
            sendErrorResponse((HttpServletResponse) response, ErrorCode.INVALID_JWT);
        }
        chain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken){
        Authentication authentication = jwtResolver.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        ObjectMapper mapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().println(mapper.writeValueAsString(errorResponse));
        response.flushBuffer();
    }
}
