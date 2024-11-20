package com.ieum.be.domain.recipe.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    /*private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recommendation/{recipeId}")
    public ResponseEntity<RecipeDto> getRecipeRecommendation(@PathVariable Long recipeId) {
        return ResponseEntity.ok(recipeService.getRecipeRecommendation(recipeId));
    }

    @GetMapping("/recommendation")
    public ResponseEntity<List<RecipeDto>> getRecipeRecommendations() {
        return ResponseEntity.ok(recipeService.getRecipeRecommendations());
    }

    @GetMapping("/options")
    public ResponseEntity<List<OptionDto>> getRecipeOptions() {
        return ResponseEntity.ok(recipeService.getRecipeOptions());
    }

    @PostMapping("/generate")
    public ResponseEntity<RecipeDto> generateRecipe(@RequestBody RecipeDto recipeDto) {
        return ResponseEntity.ok(recipeService.generateRecipe(recipeDto));
    }*/
}
