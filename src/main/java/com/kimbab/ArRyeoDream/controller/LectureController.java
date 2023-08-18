package com.kimbab.ArRyeoDream.controller;

import com.kimbab.ArRyeoDream.lecture.dto.LectureApplicationRequestDTO;
import com.kimbab.ArRyeoDream.lecture.dto.LectureCommentRequestDTO;
import com.kimbab.ArRyeoDream.lecture.dto.LectureRequestDTO;
import com.kimbab.ArRyeoDream.lecture.service.LectureServiceImpl;
import com.kimbab.ArRyeoDream.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecture")
public class LectureController {
    private final LectureServiceImpl lectureService;

    // lecture board api
    @GetMapping("/boards")
    public ResponseEntity<?> getLectureList(@PageableDefault(size=20) Pageable pageable, @RequestParam(value = "sort", required = false, defaultValue = "newest") String sort){
        return ResponseEntity.ok().body(lectureService.getLectureList(pageable, sort));
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<?> getLectureDetail(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok().body(lectureService.getLectureDetail(id));
    }

    @PostMapping("/board")
    public ResponseEntity<?> saveLecture(@RequestBody LectureRequestDTO lectureRequestDTO, @AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(lectureService.saveLecture(lectureRequestDTO, user));
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<?> updateLecture(@PathVariable(name = "id") Long id, @RequestBody LectureRequestDTO lectureRequestDTO, @AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(lectureService.updateLecture(id, lectureRequestDTO, user));
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> deleteLecture(@PathVariable(name = "id") Long id, @AuthenticationPrincipal User user){
        lectureService.deleteLecture(id, user);
        return ResponseEntity.ok().body(null);
    }

    // board comment api
    @PostMapping("/comment/{id}")
    public ResponseEntity<?> saveComment(@RequestBody LectureCommentRequestDTO lectureCommentRequestDTO, @PathVariable(name = "id") Long id){
        return ResponseEntity.ok().body(lectureService.saveCommentInLecture(lectureCommentRequestDTO, id));
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(@RequestBody LectureCommentRequestDTO lectureCommentRequestDTO, @PathVariable(name = "id") Long id){
        return ResponseEntity.ok().body(lectureService.updateCommentInLecture(lectureCommentRequestDTO, id));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") Long id){
        lectureService.deleteCommentInLecture(id);
        return ResponseEntity.ok().body(null);
    }

    // lecture application
    @PostMapping("/application/{id}")
    public ResponseEntity<Long> applicationLecture(@PathVariable(name = "id") Long id, @RequestBody LectureApplicationRequestDTO lectureApplicationRequestDTO){
        return ResponseEntity.ok().body(lectureService.applicationLecture(id, lectureApplicationRequestDTO));
    }

}
