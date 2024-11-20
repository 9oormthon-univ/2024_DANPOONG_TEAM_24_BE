package com.ieum.be.domain.recipe.service;

import com.ieum.be.domain.recipe.dto.AiGeneratedRecipeDto;
import com.ieum.be.domain.recipe.dto.RecipeListDto;
import com.ieum.be.domain.recipe.dto.RecipeOptionDto;
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

    public RecipeListDto getRecipeById(Integer recipeId) {
        return this.recipeRepository.findById(recipeId)
                .map(recipe -> new RecipeListDto(
                        recipe.getRecipeId(),
                        recipe.getRecipeName()
                ))
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + recipeId));
    }

    public List<RecipeListDto> getAllRecommendedRecipes() {
        return this.recipeRepository.findAll().stream()
                .map(recipe -> new RecipeListDto(
                        recipe.getRecipeId(),
                        recipe.getRecipeName()
                ))
                .collect(Collectors.toList());
    }

    public List<RecipeOptionDto> getRecipeOptions() {
        // 옵션 데이터를 하드코딩하거나 필요에 따라 Repository에서 조회
        return List.of(
                new RecipeOptionDto("최대 금액 선택", List.of(
                        new OptionDTO("8000원", 8000),
                        new OptionDTO("9000원", 9000)
                )),
                new RecipeOptionDto("편의점 선택", List.of(
                        new OptionDTO("GS25", "GS25"),
                        new OptionDTO("CU", "CU"),
                        new OptionDTO("세븐일레븐", "7ELEVEN")
                )),
                new RecipeOptionDto("키워드", List.of(
                        new OptionDTO("든든한 식사", "nutritious"),
                        new OptionDTO("맛있는 식사", "delicious"),
                        new OptionDTO("가성비 식사", "good_price")
                ))
        );
    }

    public AiGeneratedRecipeDto generateRecipe(AiRecipeRequest request) {
        // AI 연동 로직 추가
        String generatedResult = "This is AI generated result based on: " + request.toString();
        return new AiGeneratedRecipeDto(generatedResult);
    }
}
