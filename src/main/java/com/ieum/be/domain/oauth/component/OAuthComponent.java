package com.ieum.be.domain.oauth.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OAuthComponent {
    @Value("${oauth.kakao.api_key}")
    public String CLIENT_ID;

    @Value("${oauth.kakao.auth_url}")
    public String AUTH_URI;

    @Value("${oauth.kakao.api_url}")
    public String API_URI;

    public final String GRANT_TYPE = "authorization_code";
}
