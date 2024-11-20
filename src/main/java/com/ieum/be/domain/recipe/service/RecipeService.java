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

    // 레시피 옵션 정보 조회
    public List<AiRecipeRequest> getRecipeOptions() {
        return List.of(
                new AiRecipeRequest("최대 금액 선택", List.of(
                        new OptionDto("8000원", 8000),
                        new OptionDto("9000원", 9000),
                        new OptionDto("10000원", 10000)
                )),
                new AiRecipeRequest("편의점 선택", List.of(
                        new OptionDto("GS25", "GS25"),
                        new OptionDto("CU", "CU"),
                        new OptionDto("세븐일레븐", "7ELEVEN")
                )),
                new AiRecipeRequest("키워드", List.of(
                        new OptionDto("든든한 식사", "nutritious"),
                        new OptionDto("맛있는 식사", "delicious"),
                        new OptionDto("가성비 식사", "good_price")
                ))
        );
    }

    // AI 레시피 생성
    public AiRecipeResponse generateAiRecipe(AiRecipeRequest request) {
        // Here we can implement logic to generate an AI-based recipe using the request details.
        // For now, let's assume we return a simple placeholder string as the result.
        String generatedRecipe = "Generated Recipe based on: " + request.getDisplay();
        return new AiRecipeResponse(generatedRecipe);
    }
}
