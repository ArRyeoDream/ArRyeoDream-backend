package com.kimbab.ArRyeoDream.lecture.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureCommentRequestDTO {
    private String name;
    private String phone;
    private String content;
}
