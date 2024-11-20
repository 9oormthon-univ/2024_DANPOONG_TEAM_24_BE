package com.ieum.be.domain.recipe.controller;

import com.ieum.be.domain.recipe.dto.RecipeDto;
import com.ieum.be.domain.recipe.dto.RecipeListDto;
import com.ieum.be.domain.recipe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) { this.recipeService = recipeService; }

    // 추천 레시피 조회
    @GetMapping("/recommendation/{recipe_id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable("recipe_id") Integer recipeId) {
        RecipeDto recipe = recipeService.getRecipeById(recipeId);
        return ResponseEntity.ok(recipe);
    }

    // 추천 레시피 리스트 조회
    @GetMapping("/recommendation")
    public ResponseEntity<List<RecipeListDto>> getAllRecommendedRecipes() {
        List<RecipeListDto> recipes = recipeService.getAllRecommendedRecipes();
        return ResponseEntity.ok(recipes);
    }
}
