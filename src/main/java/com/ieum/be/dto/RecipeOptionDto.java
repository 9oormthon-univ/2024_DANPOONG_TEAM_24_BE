package com.ieum.be.dto;

import java.util.List;

public class RecipeOptionDto {
    private String display;
    private List<OptionValue> value;

    public RecipeOptionDto(String display, List<OptionValue> value) {
        this.display = display;
        this.value = value;
    }

    // 내부 클래스: 옵션의 상세값 구조
    public static class OptionValue {
        private String display;
        private Object value; // 금액은 숫자, 키워드는 문자열이므로 Object로 처리

        public OptionValue(String display, Object value) {
            this.display = display;
            this.value = value;
        }
    }
}

