package com.ieum.be.domain.recipe.dto;

public class RecipeListDto {
    private Integer recipeId;
    private String recipeName;

    public RecipeListDto(Integer recipeId, String recipeName) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
    }

    public RecipeListDto() {}
}

