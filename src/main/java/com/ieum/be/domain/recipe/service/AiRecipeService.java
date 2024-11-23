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
import java.util.Map;
import java.util.Optional;

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

    // 프롬프트 생성
    private String buildPrompt(AiRecipeRequestDto request, String userText) {

        String prompt = String.format(
                "편의점 %s에서 %s원으로 %s한 식사를 구성해주세요.%n%n" +
                        "다음과 같은 형식으로 답변을 작성하세요:%n" +
                        "1. 각 제품의 이름, 가격, 특징을 목록으로 제시%n" +
                        "2. 총합 계산%n" +
                        "3. 추천 식사 구성을 작성%n%n" +
                        "예시:%n" +
                        "1. 추천 제품 목록:%n" +
                        "- 리얼프라이스 슬라이스 닭가슴살 갈릭맛 (2,300원): 부드럽고 촉촉한 닭가슴살%n" +
                        "- 샐러드를 만드는 사람들 치킨 앤 에그 콥 샐러드 (4,100원): 신선한 채소와 단백질%n" +
                        "- 리얼프라이스 플레인 요거트 (1,500원): 무가당 플레인 요거트%n" +
                        "2. 총합: 7,900원%n" +
                        "3. 추천 구성: 닭가슴살과 샐러드를 조합해 메인 식사를, 요거트를 디저트로 구성%n%n",
                request.getValue().get(1).getValue(),
                request.getValue().get(0).getValue(),
                request.getValue().get(2).getValue()
        );

        // 추가 사용자 입력 추가
        if (Optional.ofNullable(userText).filter(text -> !text.isEmpty()).isPresent()) {
            prompt += "추가 참고 사항: " + userText;
        }

        return prompt;

        /*StringBuilder prompt = new StringBuilder();

        // Option 추출해 넣기
        prompt.append("편의점 ").append(request.getValue().get(1).getValue())
                .append("에서 ").append(request.getValue().get(0).getValue()).append("원으로 ")
                .append(request.getValue().get(2).getValue()).append("한 식사를 구성해주세요.\n\n");
        prompt.append("다음과 같은 형식으로 답변을 작성하세요:\n");
        prompt.append("1. 각 제품의 이름, 가격, 특징을 목록으로 제시\n");
        prompt.append("2. 총합 계산\n");
        prompt.append("3. 추천 식사 구성을 작성\n\n");
        prompt.append("예시:\n");
        prompt.append("1. 추천 제품 목록:\n");
        prompt.append("- 리얼프라이스 슬라이스 닭가슴살 갈릭맛 (2,300원): 부드럽고 촉촉한 닭가슴살\n");
        prompt.append("- 샐러드를 만드는 사람들 치킨 앤 에그 콥 샐러드 (4,100원): 신선한 채소와 단백질\n");
        prompt.append("- 리얼프라이스 플레인 요거트 (1,500원): 무가당 플레인 요거트\n");
        prompt.append("2. 총합: 7,900원\n");
        prompt.append("3. 추천 구성: 닭가슴살과 샐러드를 조합해 메인 식사를, 요거트를 디저트로 구성\n\n");

        // Text도 선택적으로 추가
        if (userText != null && !userText.isEmpty()) {
            prompt.append("추가 참고 사항: ").append(userText);
        }

        return prompt.toString();*/
    }
}