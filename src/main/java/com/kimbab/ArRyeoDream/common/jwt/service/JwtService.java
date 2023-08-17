package com.kimbab.ArRyeoDream.common.jwt.service;

import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.common.jwt.util.JwtProvider;
import com.kimbab.ArRyeoDream.common.jwt.util.JwtResolver;
import com.kimbab.ArRyeoDream.user.dto.JwtTokenDTO;
import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.oauth.OAuth2Service;
import com.kimbab.ArRyeoDream.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProvider jwtProvider;
    private final JwtResolver jwtResolver;
    private final UserService userService;
    private final OAuth2Service oAuth2Service;

    public JwtTokenDTO issue(String oAuth2AccessToken){
        String oAuth2Id = oAuth2Service.getOAuth2User(oAuth2AccessToken)
                .getOauth2Id();

        User user = userService.findByOauth2Id(oAuth2Id);
        if(user == null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        String accessToken = jwtProvider.createAccessToken(user.getOauth2Id());
        String refreshToken = jwtProvider.createRefreshToken();

        return JwtTokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtTokenDTO refresh(String refreshToken, String oauth2Id){
        if(isValidate(refreshToken)){
            throw new BusinessException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        String accessToken = jwtProvider.createAccessToken(oauth2Id);
        if(isRefreshable(refreshToken)){
            String newRefreshToken = jwtProvider.createRefreshToken();
        }
        return JwtTokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Boolean isValidate(String refreshToken){
        Date now = new Date();
        return !jwtResolver.isExpired(refreshToken, now);
    }

    private Boolean isRefreshable(String refreshToken){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 3);
        return !jwtResolver.isExpired(refreshToken, calendar.getTime());
    }
}
