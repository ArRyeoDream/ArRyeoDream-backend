package com.kimbab.ArRyeoDream.domain;


import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private Integer level;

    @Builder
    public User(Long id, String email, String name, Integer level) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.level = level;
    }
}
