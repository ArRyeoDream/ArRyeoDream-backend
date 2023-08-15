package com.kimbab.ArRyeoDream.community.dto;

import com.kimbab.ArRyeoDream.community.entity.CommunityComment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityCommentResponseDTO {
    private Long id;
    private String nickname;
    private String content;

    public CommunityCommentResponseDTO(CommunityComment communityComment){
        id = communityComment.getId();
        nickname = "유저 개발 이후 추가";
        content = communityComment.getContent();
    }
}
