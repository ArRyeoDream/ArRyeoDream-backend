package com.kimbab.ArRyeoDream.lecture.dto;

import com.kimbab.ArRyeoDream.lecture.entity.LectureImage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureImageResponseDTO {
    private Long lectureImageId;
    private String link;

    public LectureImageResponseDTO(LectureImage lectureImage){
        lectureImageId = lectureImage.getId();
        link = lectureImage.getLink();
    }
}
