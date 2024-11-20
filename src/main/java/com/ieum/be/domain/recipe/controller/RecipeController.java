package com.ieum.be.domain.recipe.controller;

import com.ieum.be.domain.recipe.dto.AiRecipeRequest;
import com.ieum.be.domain.recipe.dto.AiRecipeResponse;
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

    // 레시피 옵션 정보 조회
    @GetMapping("/options")
    public ResponseEntity<?> getRecipeOptions() {
        List<AiRecipeRequest> options = recipeService.getRecipeOptions();
        return ResponseEntity.ok(options);
    }

    // AI 레시피 생성
    @PostMapping("/generate")
    public ResponseEntity<?> generateAiRecipe(@RequestBody AiRecipeRequest request) {
        AiRecipeResponse response = recipeService.generateAiRecipe(request);
        return ResponseEntity.ok(response);
    }
}
