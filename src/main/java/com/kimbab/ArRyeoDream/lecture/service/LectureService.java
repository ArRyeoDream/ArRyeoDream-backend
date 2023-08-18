package com.kimbab.ArRyeoDream.lecture.service;

import com.kimbab.ArRyeoDream.common.dto.PageResult;
import com.kimbab.ArRyeoDream.lecture.dto.*;
import com.kimbab.ArRyeoDream.user.entity.User;
import org.springframework.data.domain.Pageable;

public interface LectureService {
    PageResult<LectureListResponseDTO> getLectureList(Pageable pageable, String sort);
    LectureResponseDTO getLectureDetail(Long id);
    Long saveLecture(LectureRequestDTO lectureRequestDTO, User user);
    Long updateLecture(Long id, LectureRequestDTO lectureRequestDTO, User user);
    void deleteLecture(Long id, User user);
    //댓글 관련
    Long saveCommentInLecture(LectureCommentRequestDTO lectureCommentRequestDTO, Long id);
    Long updateCommentInLecture(LectureCommentRequestDTO lectureCommentRequestDTO, Long id);
    void deleteCommentInLecture(Long id);
    //신청 관련
    Long applicationLecture(Long id, LectureApplicationRequestDTO lectureApplicationRequestDTO);
}
