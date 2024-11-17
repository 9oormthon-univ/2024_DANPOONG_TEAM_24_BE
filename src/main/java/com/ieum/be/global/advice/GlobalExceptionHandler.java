package com.ieum.be.global.advice;

import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public GeneralResponse handleException(Exception exception) {
        if (exception instanceof GlobalException e) {
            return e.getStatus();
        }

        return GeneralResponse.INTERNAL_SERVER_ERROR;
    }
}
