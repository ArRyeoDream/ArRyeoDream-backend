package com.kimbab.ArRyeoDream.config;

import com.kimbab.ArRyeoDream.jwt.JwtAuthenticationFilter;
import com.kimbab.ArRyeoDream.jwt.JwtTokenProvider;
import com.kimbab.ArRyeoDream.oauth2.CustomOAuth2TokenResponseClient;
import com.kimbab.ArRyeoDream.oauth2.OAuth2AuthenticationFailureHandler;
import com.kimbab.ArRyeoDream.oauth2.OAuth2AuthenticationSuccessHandler;
import com.kimbab.ArRyeoDream.repository.CookieAuthorizationRequestRepository;
import com.kimbab.ArRyeoDream.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import java.security.Security;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfigure  {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //httpBasic, csrf, formLogin, rememberMe, logout, session disable
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //요청에 대한 권한 설정
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        //oauth2Login
        http.oauth2Login()
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint.accessTokenResponseClient(new CustomOAuth2TokenResponseClient()))
                .authorizationEndpoint().baseUri("/oauth2/authorize")  // 소셜 로그인 url
                .authorizationRequestRepository(cookieAuthorizationRequestRepository)  // 인증 요청을 cookie 에 저장
                .and()
                .redirectionEndpoint().baseUri("/login/oauth2/code/*")  // 소셜 인증 후 redirect url
                .and()
                //userService()는 OAuth2 인증 과정에서 Authentication 생성에 필요한 OAuth2User 를 반환하는 클래스를 지정한다.
                .userInfoEndpoint().userService(customOAuth2UserService)  // 회원 정보 처리
                .and()

                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);


        http.logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");

        //jwt filter 설정
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

}
