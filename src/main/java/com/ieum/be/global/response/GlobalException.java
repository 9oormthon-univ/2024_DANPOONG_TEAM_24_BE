package com.ieum.be.global.response;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final GeneralResponse status;

    public GlobalException(GeneralResponse status) {
        this.status = status;
    }
}
