package com.ieum.be.global.advice;

import com.ieum.be.global.response.BaseResponse;
import com.ieum.be.global.response.BindingResultMessage;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public GeneralResponse handleException(Exception exception){
        if (exception instanceof GlobalException e) {
            return e.getStatus();
        }

        if(exception instanceof NoResourceFoundException) {
            return GeneralResponse.NOT_FOUND;
        }

        if(exception instanceof HttpMessageNotReadableException) {
            return GeneralResponse.REQUEST_BODY_NOT_READABLE;
        }

        if(exception instanceof HttpRequestMethodNotSupportedException){
            return GeneralResponse.REJECTED;
        }

        if(exception instanceof MissingRequestHeaderException){
            return GeneralResponse.REJECTED;
        }

        return GeneralResponse.INTERNAL_SERVER_ERROR;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private BaseResponse<?> methodValidException(MethodArgumentNotValidException e) {
        List<String> errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(BindingResultMessage::of).toList();

        return BaseResponse.builder()
                .code(HttpStatus.NOT_ACCEPTABLE.value())
                .message(errorMessage.toString())
                .build();
    }
}
