package com.kimbab.ArRyeoDream.controller;

import com.kimbab.ArRyeoDream.dto.UserResponseDto;
import com.kimbab.ArRyeoDream.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

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
