package com.kimbab.ArRyeoDream.community.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private List<CommunityImageResponseDTO> images;
    private List<CommunityCommentResponseDTO> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
