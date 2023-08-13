package com.kimbab.ArRyeoDream.community.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "community_image")
public class CommunityImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_image_id")
    private Long id;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;
}
