package com.kimbab.ArRyeoDream.controller;

import com.kimbab.ArRyeoDream.community.dto.CommunityCommentRequestDTO;
import com.kimbab.ArRyeoDream.community.dto.CommunityRequestDTO;
import com.kimbab.ArRyeoDream.community.service.CommunityServiceImpl;
import com.kimbab.ArRyeoDream.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {
    private final CommunityServiceImpl communityService;

    // lecture board api
    @GetMapping("/boards")
    public ResponseEntity<?> getLectureList(@PageableDefault(size=20) Pageable pageable){
        return ResponseEntity.ok().body(communityService.getCommunityList(pageable));
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<?> getLectureDetail(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok().body(communityService.getCommunityDetail(id));
    }

    @PostMapping("/board")
    public ResponseEntity<?> saveLecture(@RequestBody CommunityRequestDTO communityRequestDTO, @AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(communityService.saveCommunity(communityRequestDTO, user));
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<?> updateLecture(@PathVariable(name = "id") Long id, @RequestBody CommunityRequestDTO communityRequestDTO, @AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(communityService.updateCommunity(id, communityRequestDTO, user));
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> deleteLecture(@PathVariable(name = "id") Long id, @AuthenticationPrincipal User user){
        communityService.deleteCommunity(id, user);
        return ResponseEntity.ok().body(null);
    }

    // board comment api
    @PostMapping("/comment/{id}")
    public ResponseEntity<?> saveComment(@RequestBody CommunityCommentRequestDTO communityCommentRequestDTO, @PathVariable(name = "id") Long id, @AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(communityService.saveCommentInCommunity(communityCommentRequestDTO, id, user));
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(@RequestBody CommunityCommentRequestDTO communityCommentRequestDTO, @PathVariable(name = "id") Long id, @AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(communityService.updateCommentInCommunity(communityCommentRequestDTO, id, user));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") Long id, @AuthenticationPrincipal User user){
        communityService.deleteCommentInCommunity(id, user);
        return ResponseEntity.ok().body(null);
    }

}
