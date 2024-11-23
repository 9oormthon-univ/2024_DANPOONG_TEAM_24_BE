package com.ieum.be.domain.recipe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ieum.be.domain.recipe.dto.AiRecipeRequestDto;
import com.ieum.be.domain.recipe.dto.AiRecipeResponseDto;
import com.ieum.be.domain.recipe.dto.OptionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

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
        return new AiRecipeResponseDto(generatedRecipe);
    }

    // API 호출
    private String callChatGptApi(String prompt) {

        // 요청 준비
        String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";

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

    // 프롬프트 생성
    private String buildPrompt(AiRecipeRequestDto request, String userText) {
        StringBuilder prompt = new StringBuilder();

        // Option 추출해 넣기
        prompt.append("금액 ").append(request.getValue().get(0).getValue()).append("원으로, ");
        prompt.append("편의점 ").append(request.getValue().get(1).getValue()).append("에서의 ");
        prompt.append("키워드 ").append(request.getValue().get(2).getValue()).append(" 관련 식사를 구성해주세요. ");

        // Text도 선택적으로 추가
        if (userText != null && !userText.isEmpty()) {
            prompt.append("추가적으로 다음 내용도 참고해주세요. ").append(userText);
        }

        return prompt.toString();
    }
}