package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeListDto {
    private Integer recipeId;
    private String recipeName;

    public RecipeListDto() {
    }

    public RecipeListDto(Integer recipeId, String recipeName) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
    }
}

