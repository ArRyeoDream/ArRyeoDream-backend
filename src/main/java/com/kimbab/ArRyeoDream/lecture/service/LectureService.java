package com.kimbab.ArRyeoDream.lecture.service;

import com.kimbab.ArRyeoDream.common.dto.PageResult;
import com.kimbab.ArRyeoDream.lecture.dto.LectureCommentRequestDTO;
import com.kimbab.ArRyeoDream.lecture.dto.LectureListResponseDTO;
import com.kimbab.ArRyeoDream.lecture.dto.LectureRequestDTO;
import com.kimbab.ArRyeoDream.lecture.dto.LectureResponseDTO;
import org.springframework.data.domain.Pageable;

public interface LectureService {
    PageResult<LectureListResponseDTO> getLectureList(Pageable pageable, String sort);
    LectureResponseDTO getLectureDetail(Long id);
    Long saveLecture(LectureRequestDTO lectureRequestDTO);
    Long updateLecture(Long id, LectureRequestDTO lectureRequestDTO);
    void deleteLecture(Long id);
    //댓글 관련
    Long saveCommentInLecture(LectureCommentRequestDTO lectureCommentRequestDTO, Long id);
    Long updateCommentInLecture(LectureCommentRequestDTO lectureCommentRequestDTO, Long id);
    void deleteCommentInLecture(Long id);
}
