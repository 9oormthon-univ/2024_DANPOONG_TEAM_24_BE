package com.ieum.be.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AiRecipeResponseDto {
    private List<String> recipeParagraphs; // 문단별 데이터
    private String selectedCost;
    private String selectedConvenienceStore;
    private String koreanKeyword; // 선택한 키워드의 한국어 버전

    public AiRecipeResponseDto(List<String> recipeParagraphs, String selectedCost, String selectedConvenienceStore, String koreanKeyword) {
        this.recipeParagraphs = recipeParagraphs;
        this.selectedCost = selectedCost;
        this.selectedConvenienceStore = selectedConvenienceStore;
        this.koreanKeyword = koreanKeyword;
    }
}
