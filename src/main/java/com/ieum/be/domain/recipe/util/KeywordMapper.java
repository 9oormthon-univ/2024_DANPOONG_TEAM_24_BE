package com.ieum.be.domain.recipe.util;


import java.util.HashMap;
import java.util.Map;

public class KeywordMapper {
    private static final Map<String, String> keywordToDisplayMap = new HashMap<>();

    static {
        keywordToDisplayMap.put("vitamin", "상큼한 비타민");
        keywordToDisplayMap.put("nutritious", "에너지 넘치는 영양소");
        keywordToDisplayMap.put("healthy_low_sugar", "건강한 저당");
        keywordToDisplayMap.put("dietary_fiber", "아삭한 식이섬유");
        keywordToDisplayMap.put("balanced_diet", "균형 잡힌 식단");
        keywordToDisplayMap.put("low_calorie", "가벼운 저칼로리");
    }

    public static String getDisplayValue(String keyword) {
        return keywordToDisplayMap.getOrDefault(keyword, keyword); // 매핑 없으면 원본 키워드 반환
    }
}
