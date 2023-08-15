package com.kimbab.ArRyeoDream.community.dto;

import com.kimbab.ArRyeoDream.community.entity.CommunityImage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityImageResponseDTO {
    private Long id;
    private String link;

    public CommunityImageResponseDTO(CommunityImage communityImage){
        id = communityImage.getId();
        link = communityImage.getLink();
    }
}
