package com.ieum.be.domain.recipe.service;

import com.ieum.be.domain.recipe.dto.*;
import com.ieum.be.domain.recipe.entity.Recipe;
import com.ieum.be.domain.recipe.repository.RecipeRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // 추천 레시피 조회
    public RecipeDto getRecipeById(Integer recipeId) {
        Recipe recipe = recipeRepository.findRecipeByRecipeId(recipeId)
                .orElseThrow(() -> new GlobalException(GeneralResponse.NOT_FOUND));

        return new RecipeDto(
                recipe.getRecipeId(),
                recipe.getRecipeName(),
                recipe.getDescription()
        );
    }

    // 추천 레시피 리스트 조회
    public List<RecipeListDto> getAllRecommendedRecipes() {
        List<Recipe> recipes = recipeRepository.findTop10ByOrderByRecipeIdDesc();
        return recipes.stream()
                .map(recipe -> new RecipeListDto(recipe.getRecipeId(), recipe.getRecipeName()))
                .toList();
    }
}
