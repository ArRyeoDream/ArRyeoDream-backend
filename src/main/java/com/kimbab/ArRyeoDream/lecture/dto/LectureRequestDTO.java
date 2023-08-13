package com.kimbab.ArRyeoDream.lecture.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureRequestDTO {
    private String title;
    private String intro;
    private List<String> region;
    private List<String> week;
    private List<String> images;
}
