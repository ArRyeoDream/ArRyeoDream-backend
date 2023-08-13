package com.kimbab.ArRyeoDream.lecture.dto;

import com.kimbab.ArRyeoDream.lecture.entity.LectureComment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureCommentResponseDTO {
    private Long id;
    private String name;
    private String content;

    public LectureCommentResponseDTO(LectureComment lectureComment){
        id = lectureComment.getId();
        name = lectureComment.getAttendee().getName();
        content = lectureComment.getContent();
    }
}
