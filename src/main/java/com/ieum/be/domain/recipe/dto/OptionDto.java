package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionDto {
    private String display;
    private Object value;

    public OptionDto(String display, Object value) {
        this.display = display;
        this.value = value;
    }
}
