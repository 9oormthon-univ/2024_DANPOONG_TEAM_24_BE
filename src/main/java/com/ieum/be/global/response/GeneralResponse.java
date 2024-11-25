package com.ieum.be.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GeneralResponse {
    /**
     * 2XX
     */
    OK(HttpStatus.OK.value(), "Success"),

    /**
     * 4XX
     */
    REJECTED(HttpStatus.NOT_ACCEPTABLE.value(), "Request rejected"),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "Not enough permission"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "Unauthenticated"),

    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Expired JWT"),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT"),

    NO_JWT_TOKEN(HttpStatus.NOT_ACCEPTABLE.value(), "No token provided"),

    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Resource not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "User not found"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Post not found"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Comment not found"),
    POST_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Post category not found"),
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Location not found"),

    USER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "User already exists"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "Wrong password"),

    REQUEST_BODY_NOT_READABLE(HttpStatus.BAD_REQUEST.value(), "Request body not present"),
    /**
     * 5xx
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown error occurred"),
    ;

    private final int code;
    private final String message;

    GeneralResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}