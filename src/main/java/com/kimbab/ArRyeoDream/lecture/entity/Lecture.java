package com.kimbab.ArRyeoDream.lecture.entity;

import com.kimbab.ArRyeoDream.common.entity.BaseBoardEntity;
import com.kimbab.ArRyeoDream.lecture.dto.LectureListResponseDTO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "lecture")
public class Lecture extends BaseBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "intro")
    private String intro;

    @Column(name = "region")
    private String region;

    @Column(name = "week")
    private String week;

    @Column(name = "views")
    private Long views;

    @Column(name = "user_id")
    private Long userId;

    public LectureListResponseDTO toLectureListResponseDTO(Lecture lecture, String imageLink){
        return new LectureListResponseDTO(
                lecture.getId(),
                lecture.getTitle(),
                Arrays.asList(lecture.getRegion().trim().split("/")),
                Arrays.asList(lecture.getWeek().trim().split("/")),
                imageLink,
                views
        );
    }
}
