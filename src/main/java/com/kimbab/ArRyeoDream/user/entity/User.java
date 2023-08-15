package com.kimbab.ArRyeoDream.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private Integer level;

    @Builder
    public User(String email, String nickname, Integer level){
        this.email = email;
        this.nickname = nickname;
        this.level = level;
    }
}
