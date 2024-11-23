package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AiRecipeResponseDto {
    private List<String> recipeParagraphs; // 문단별 데이터를 저장

    public AiRecipeResponseDto(List<String> recipeParagraphs) {
        this.recipeParagraphs = recipeParagraphs;
    }
}
