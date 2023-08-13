package com.kimbab.ArRyeoDream.lecture.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureListResponseDTO {
    private Long lectureId;
    private String title;
    private List<String> region;
    private List<String> week;
    private String image;
    private Long views;
}
