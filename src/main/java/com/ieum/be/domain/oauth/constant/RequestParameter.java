package com.ieum.be.domain.oauth.constant;

import lombok.Getter;

@Getter
public enum RequestParameter {
    CODE("code"),
    GRANT_TYPE("grant_type"),
    CLIENT_ID("client_id"),
    REDIRECT_URI("redirect_uri");

    private final String value;

    RequestParameter(String value) {
        this.value = value;
    }
}
