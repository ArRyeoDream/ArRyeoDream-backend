package com.kimbab.ArRyeoDream.lecture.entity;

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
@Table(name = "lecture_image")
public class LectureImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_image_id")
    private Long id;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
