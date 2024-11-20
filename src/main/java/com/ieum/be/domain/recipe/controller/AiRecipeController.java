package com.ieum.be.domain.recipe.controller;

import com.ieum.be.domain.recipe.dto.AiRecipeRequestDto;
import com.ieum.be.domain.recipe.dto.AiRecipeResponseDto;
import com.ieum.be.domain.recipe.service.AiRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class AiRecipeController {

    private final AiRecipeService aiRecipeService;

    public AiRecipeController(AiRecipeService aiRecipeService) {
        this.aiRecipeService = aiRecipeService;
    }

    // 레시피 옵션 정보 조회
    @GetMapping("/options")
    public ResponseEntity<List<AiRecipeRequestDto>> getRecipeOptions() {
        List<AiRecipeRequestDto> options = aiRecipeService.getRecipeOptions();
        return ResponseEntity.ok(options);
    }

    // AI 레시피 생성
    @PostMapping("/generate")
    public ResponseEntity<AiRecipeResponseDto> generateAiRecipe(
            @RequestBody AiRecipeRequestDto request,
            @RequestParam(value = "userText", required = false) String userText) {
        AiRecipeResponseDto response = aiRecipeService.generateAiRecipe(request, userText);
        return ResponseEntity.ok(response);
    }
}
