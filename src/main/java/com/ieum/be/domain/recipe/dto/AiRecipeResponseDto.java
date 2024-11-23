package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiRecipeResponseDto {
    private String recipeResult;

    public AiRecipeResponseDto(String recipeResult) {
        this.recipeResult = recipeResult;
    }
}
