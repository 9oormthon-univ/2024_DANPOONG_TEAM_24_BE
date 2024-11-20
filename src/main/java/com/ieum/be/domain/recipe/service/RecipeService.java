package com.ieum.be.domain.recipe.service;

import com.ieum.be.domain.recipe.dto.AiRecipeResponse;
import com.ieum.be.domain.recipe.dto.OptionDto;
import com.ieum.be.domain.recipe.dto.RecipeListDto;
import com.ieum.be.domain.recipe.dto.AiRecipeRequest;
import com.ieum.be.domain.recipe.entity.Recipe;
import com.ieum.be.domain.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // 추천 레시피 조회
    public Recipe getRecipeById(Integer recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));
    }

    // 추천 레시피 리스트 조회
    public List<RecipeListDto> getAllRecommendedRecipes() {
        List<Recipe> recipes = recipeRepository.findTop10ByOrderByRecipeIdDesc();  // Example for top recipes
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
