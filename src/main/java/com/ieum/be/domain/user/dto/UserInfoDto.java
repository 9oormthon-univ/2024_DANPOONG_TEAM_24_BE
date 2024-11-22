package com.ieum.be.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoDto {
    private String email;
    private String name;
    private String profileImage;

    public UserInfoDto(String email, String nickname, String profileImage) {
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
