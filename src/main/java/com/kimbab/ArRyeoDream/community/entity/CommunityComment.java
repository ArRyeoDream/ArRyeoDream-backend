package com.kimbab.ArRyeoDream.community.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "community_comment")
public class CommunityComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_comment_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
    @Column(name = "userId")
    private Long userId;
}
