package com.ieum.be.global.advice;

import com.ieum.be.global.response.BaseResponse;
import com.ieum.be.global.response.GeneralResponse;
import jakarta.annotation.Nonnull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(
            @Nonnull MethodParameter returnType,
            @Nonnull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        // JSON으로 변환 가능한 경우에만 변환
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @Nonnull MethodParameter returnType,
            @Nonnull MediaType selectedContentType,
            @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @Nonnull ServerHttpRequest request,
            @Nonnull ServerHttpResponse response
    ) {
        if (body instanceof GeneralResponse generalResponse) {
            response.setStatusCode(HttpStatusCode.valueOf(generalResponse.getCode()));

            return BaseResponse.builder()
                    .code(generalResponse.getCode())
                    .message(generalResponse.getMessage())
                    .data(null)
                    .build();
        }

        if (body instanceof BaseResponse<?> baseResponse) {
            response.setStatusCode(HttpStatusCode.valueOf(baseResponse.getCode()));

            return baseResponse;
        }

        return BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .data(body)
                .build();
    }
}