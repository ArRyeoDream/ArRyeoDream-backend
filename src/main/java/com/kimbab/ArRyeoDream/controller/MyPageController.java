package com.kimbab.ArRyeoDream.controller;

import com.kimbab.ArRyeoDream.mypage.service.MyPageServiceImpl;
import com.kimbab.ArRyeoDream.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MyPageServiceImpl myPageService;

    @GetMapping("/lecture")
    public ResponseEntity<?> getMyLectureList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(myPageService.getMyLectureList(user));
    }

    @GetMapping("/community")
    public ResponseEntity<?> getMyCommunityList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(myPageService.getMyCommunityList(user));
    }
}
