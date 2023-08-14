package com.kimbab.ArRyeoDream.dto;

import com.kimbab.ArRyeoDream.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserReturnDto {
    private Long id;
    private String oauth2Id;
    private String email;
    private String name;
    private Integer level;

    public UserReturnDto(User user) {
        this.id = user.getId();
        this.oauth2Id = user.getOauth2Id();
        this.email = user.getEmail();
        this.name = user.getName();
        this.level = user.getLevel();
    }
}
