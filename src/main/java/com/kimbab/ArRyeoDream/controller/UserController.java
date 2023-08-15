package com.kimbab.ArRyeoDream.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
//
//    private KaKaoAPI kakaoapi;
//
//    public UserController(){
//        kakaoapi = new KaKaoAPI();
//    }
//
//    @PostMapping(value = "/logout")
//    public ResponseEntity<Void> logout(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        String accessToken = (String) session.getAttribute("access_token");
//
//        if(accessToken!=null&&!"".equals(accessToken)){
//            kakaoapi.logout(accessToken);
//            session.removeAttribute("access_token");
//            session.removeAttribute("user");
//
//            System.out.println(" 로그아웃 " );
//        }
//        return ResponseEntity.ok().build();
//    }

//    private final CustomOAuth2UserService oAuth2UserService;
//    // 임시 회원 가입 컨트롤러
//    @GetMapping(value = "/test/login")
//    public ResponseEntity<UserResponseDto> testLogin(@)

//   //  회원 탈퇴
//    @DeleteMapping("/my-page/delete")
//    public ResponseEntity<HttpStatus> deleteUser(HttpServletRequest request){
//
//    }
//    // 나의 개설 강좌 조회
//    @GetMapping("/my-page/lecture")
//
//
//    // 강좌 신청자 조회
//    @GetMapping("/my-page/lecture/{lectureId}/attendee")
//

}
