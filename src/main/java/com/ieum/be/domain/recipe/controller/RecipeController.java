package com.ieum.be.domain.recipe.controller;

import com.ieum.be.domain.recipe.service.RecipeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/recommendation/{recipeId}")
    public ResponseEntity<RecipeListDTO> getRecipe(@PathVariable Long recipeId) {
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }

    @GetMapping("/recommendation")
    public ResponseEntity<List<RecipeListDTO>> getAllRecommendedRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecommendedRecipes());
    }

    @GetMapping("/options")
    public ResponseEntity<List<RecipeOptionListDTO>> getRecipeOptions() {
        return ResponseEntity.ok(recipeService.getRecipeOptions());
    }

    @PostMapping("/generate")
    public ResponseEntity<AiGeneratedRecipeDTO> generateRecipe(@RequestBody AiRecipeRequest request) {
        return ResponseEntity.ok(recipeService.generateRecipe(request));
    }
}
