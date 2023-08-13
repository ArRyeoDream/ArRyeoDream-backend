package com.kimbab.ArRyeoDream.community.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityRequestDTO {
    private String title;
    private String content;
    private List<String> images;
}
