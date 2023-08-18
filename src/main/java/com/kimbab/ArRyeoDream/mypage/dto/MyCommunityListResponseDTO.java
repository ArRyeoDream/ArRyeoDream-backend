package com.kimbab.ArRyeoDream.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MyCommunityListResponseDTO<T> {
    private T content;
}
