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
        // ChatGPT API에 대한 Request Body 구성
        var requestBody = Map.of(
                "model", "gpt-3.5-turbo",  // or another model
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.7
        );

        // API Request 전송
        Mono<String> response = webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

        // 생성된 답변 가져오기
        try {
            String apiResponse = response.block();
            var jsonNode = new ObjectMapper().readTree(apiResponse);
            return jsonNode.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
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


    /*// AI 레시피 생성
    public AiRecipeResponse generateAiRecipe(AiRecipeRequest request, String userText) {
        String convenienceStore = null;
        Integer priceRange = null;
        String keyword = null;

        // request로부터 option 추출
        for (OptionDto option : request.getValue()) {
            if ("편의점 선택".equals(option.getDisplay())) {
                convenienceStore = option.getValue().toString();
            } else if ("최대 금액 선택".equals(option.getDisplay())) {
                priceRange = Integer.parseInt(option.getValue().toString());
            } else if ("키워드".equals(option.getDisplay())) {
                keyword = option.getValue().toString();
            }
        }

        // ChatGPT API prompt 생성
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(String.format(
                "편의점 %s에서 %s원으로 %s 관련 식사를 구성해 주세요.",
                convenienceStore != null ? convenienceStore : "편의점",
                priceRange != null ? priceRange : "예산",
                keyword != null ? keyword : "키워드"
        ));

        // request에 text 포함되어 있다면 text도 프롬프트에 추가
        if (userText != null && !userText.isEmpty()) {
            promptBuilder.append(String.format(" 추가적으로 다음 내용을 참고해 주세요: \"%s\".", userText));
        }

        String prompt = promptBuilder.toString();

        String generatedRecipe = callChatGptApi(prompt);

        return new AiRecipeResponse(generatedRecipe);
    }

    private String callChatGptApi(String prompt) {
        // Implement API call logic here
        return "Generated Recipe: " + prompt;
    }
}*/
