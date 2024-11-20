package com.ieum.be.domain.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoUserDataDto {
    private String email;
    private String nickname;
    private String profileImageUrl;
}
