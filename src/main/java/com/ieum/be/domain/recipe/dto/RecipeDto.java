package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDto {
    private Integer recipeId;
    private String recipeName;
    private String description;

    public RecipeDto() {
    }

    public RecipeDto(Integer recipeId, String recipeName, String description) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.description = description;
    }
}
