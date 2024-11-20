package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AiRecipeRequest {
    private String display;
    private List<OptionDto> value;

    public AiRecipeRequest(String display, List<OptionDto> value) {
        this.display = display;
        this.value = value;
    }
}

