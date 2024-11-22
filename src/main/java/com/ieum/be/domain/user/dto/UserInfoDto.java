package com.ieum.be.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoDto {
    private String email;
    private String name;
    private String profileUrl;

    public UserInfoDto(String email, String name, String profileImage) {
        this.email = email;
        this.name = name;
        this.profileUrl = profileImage;
    }
}
