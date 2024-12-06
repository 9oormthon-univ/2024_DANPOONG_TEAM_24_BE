package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeListDto {
    private Integer recipeId;
    private String recipeName;
    private String description;
    private String imageUrl;
    private String videoUrl;

    public RecipeListDto(Integer recipeId, String recipeName, String description, String imageUrl, String videoUrl) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }
}

