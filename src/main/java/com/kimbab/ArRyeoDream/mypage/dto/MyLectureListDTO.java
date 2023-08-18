package com.kimbab.ArRyeoDream.mypage.dto;

import com.kimbab.ArRyeoDream.lecture.entity.Lecture;
import com.kimbab.ArRyeoDream.user.entity.Attendee;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyLectureListDTO {
    private Long id;
    private String title;
    private List<String> region;
    private String author;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Attendee> attendeeList;
}
