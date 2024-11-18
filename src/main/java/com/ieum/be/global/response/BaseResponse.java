package com.ieum.be.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@JsonPropertyOrder({"code", "message", "data"})
public class BaseResponse<T> {
    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Setter
    private T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse<?> of(GeneralResponse generalResponse) {
        return new BaseResponse<>(generalResponse.getCode(), generalResponse.getMessage(), null);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.fillInStackTrace();
        }

        return null;
    }
}
