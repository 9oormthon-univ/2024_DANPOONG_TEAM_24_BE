package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AiRecipeRequestDto {
    private List<OptionDto> value;  // OptionDto 리스트로 간소화
}

