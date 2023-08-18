package com.kimbab.ArRyeoDream.lecture.entity;

import com.kimbab.ArRyeoDream.user.entity.Attendee;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_list")
public class ApplicationList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_list_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;
}
