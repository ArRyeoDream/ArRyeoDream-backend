package com.kimbab.ArRyeoDream.lecture.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureResponseDTO {
    private Long lectureId;
    private String title;
    private String intro;
    private List<String> region;
    private List<String> week;
    private List<LectureImageResponseDTO> images;
    private List<LectureCommentResponseDTO> comments;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
