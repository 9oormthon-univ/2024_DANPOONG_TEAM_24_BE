package com.ieum.be.domain.recipe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ieum.be.domain.recipe.dto.AiRecipeRequestDto;
import com.ieum.be.domain.recipe.dto.AiRecipeResponseDto;
import com.ieum.be.domain.recipe.dto.OptionDto;
import com.ieum.be.domain.recipe.util.KeywordMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

@Service
public class AiRecipeService {

    private final WebClient webClient;

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    public AiRecipeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();
    }

    // 레시피 옵션 정보 조회
    public List<AiRecipeRequestDto> getRecipeOptions() {
        return List.of(
                new AiRecipeRequestDto("최대 금액 선택", List.of(
                        new OptionDto("8000원", 8000),
                        new OptionDto("9000원", 9000)
                )),
                new AiRecipeRequestDto("편의점 선택", List.of(
                        new OptionDto("GS25", "GS25"),
                        new OptionDto("CU", "CU"),
                        new OptionDto("세븐일레븐", "7ELEVEN")
                )),
                new AiRecipeRequestDto("키워드", List.of(
                        new OptionDto("상큼한 비타민", "vitamin"),
                        new OptionDto("에너지 넘치는 영양소", "nutritious"),
                        new OptionDto("건강한 저당", "healthy_low_sugar"),
                        new OptionDto("아삭한 식이섬유", "dietary_fiber"),
                        new OptionDto("균형 잡힌 식단", "balanced_diet"),
                        new OptionDto("가벼운 저칼로리", "low_calorie")
                ))
        );
    }

    // AI 레시피 생성
    public AiRecipeResponseDto generateAiRecipe(AiRecipeRequestDto request, String userText) {
        String prompt = buildPrompt(request, userText);  // 프롬프트 생성
        String generatedRecipe = callChatGptApi(prompt);  // API 호출

        // 답변 내용을 문단별로 나누기
        List<String> paragraphs = Arrays.asList(generatedRecipe.split("\n\n"));

        return new AiRecipeResponseDto(paragraphs); // 리스트로 반환
    }

    // API 호출
    private String callChatGptApi(String prompt) {

        // 요청 데이터 생성
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        // OpenAI API 호출
        Mono<String> response = webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);  // 응답을 JsonNode로 직접 처리

        // 응답 받기
        try {
            String apiResponse = response.block();
            var jsonNode = new ObjectMapper().readTree(apiResponse);
            return jsonNode.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            // 예외 로그
            System.err.println("Error while calling OpenAI API: " + e.getMessage()); // 예외 메시지 출력
            e.printStackTrace();  // 예외 스택 트레이스 출력
            throw new RuntimeException("Failed to call OpenAI API", e);
        }
    }

    private String buildPrompt(AiRecipeRequestDto request, String userText) {

        List<OptionDto> options = request.getValue();
        String store = options.size() > 1 && options.get(1).getValue() != null ? (String) options.get(1).getValue() : "Unknown Store";
        String maxAmount = options.size() > 0 && options.get(0).getValue() != null ? String.valueOf(options.get(0).getValue()) : "0";
        String insertedKeyword = options.size() > 2 && options.get(2).getValue() != null ? (String) options.get(2).getValue() : "키워드 없음";
        // 키워드 매핑
        String displayKeyword = KeywordMapper.getDisplayValue(insertedKeyword);

        System.out.println("User Text: " + userText);
        System.out.println("Display Keyword: " + displayKeyword);

        String prompt = String.format(
                "편의점 %s에서 %s원으로 %s를 만족하는 맛있는 식사를 구성해주세요.%n%n" +
                        "다음 내용을 반드시 포함하여 한국어로 답변을 작성하세요:%n" +
                        "1. 각 제품의 이름, 가격, 특징을 목록으로 제시%n" +
                        "2. 총합 계산%n" +
                        "3. 추천 식사 구성을 작성%n" +
                        "4. 요구 사항(%s)을 반드시 반영해 메뉴를 구성%n" +
                        "내용 각각을 독립적인 문단으로 작성하세요.%n",
                        "예시:%n" +
                        "%s에서 %s원으로 %s를 만족하는 맛있는 한 끼를 구성하기 위해 다음과 같은 제품들을 추천해요.%n%n" +
                        "1. 추천 제품 목록:%n" +
                        "- 리얼프라이스 슬라이스 닭가슴살 갈릭맛 (2,300원): 부드럽고 촉촉한 닭가슴살%n" +
                        "- 샐러드를 만드는 사람들 치킨 앤 에그 콥 샐러드 (4,100원): 신선한 채소와 단백질%n" +
                        "- 리얼프라이스 플레인 요거트 (1,500원): 무가당 플레인 요거트%n%n" +
                        "2. 총합: 7,900원%n%n" +
                        "3. 추천 구성: 닭가슴살과 샐러드를 조합해 메인 식사를, 요거트를 디저트로 구성%n%n",
                store, maxAmount, displayKeyword, userText, store, maxAmount, displayKeyword
        );

        System.out.println("Generated Prompt: " + prompt);
        return prompt;
    }
}
