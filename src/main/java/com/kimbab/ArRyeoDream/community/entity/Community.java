package com.kimbab.ArRyeoDream.community.entity;

import com.kimbab.ArRyeoDream.common.entity.BaseBoardEntity;
import com.kimbab.ArRyeoDream.community.dto.CommunityListResponseDTO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "community")
public class Community extends BaseBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "user_id")
    private Long userId;

    public CommunityListResponseDTO toCommunityListResponseDTO(Community community){
        return new CommunityListResponseDTO(
                community.getId(),
                community.getTitle(),
                community.getContent(),
                community.getCreatedAt(),
                community.getUpdatedAt()
        );
    }
}
