package com.kimbab.ArRyeoDream.mypage.dto;

import lombok.*;

@AllArgsConstructor
@Data
public class MyLectureListResponseDTO<T> {
    private T content;
}
