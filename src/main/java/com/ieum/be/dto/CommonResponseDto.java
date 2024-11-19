package com.ieum.be.dto;

public class CommonResponseDto<T> {
    private int code;
    private T data;
    private String message;

    // 성공 응답 생성자
    public CommonResponseDto(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = null;
    }

    // 실패 응답 생성자
    public CommonResponseDto(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}
