package com.deepak.ai_news_aggregator.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Order(2) // SECOND PRIORITY
@RequiredArgsConstructor
public class GeminiSummarizationProvider implements SummarizationProvider {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    @Value("${gemini.base.url}")
    private String baseUrl;

    @Override
    public String summarize(String content) throws Exception {

        if(content == null || content.length() < 300) {
            throw new IllegalArgumentException("Content too short for summarization");
        }

        String url = String.format(
                "%s/%s:generateContent?key=%s",
                baseUrl,
                model,
                apiKey
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of(
                                                "text",
                                                "Summarize the following news article in 4-5 concise, neutral sentences. No opinions. No markdown.\n\n"
                                                        + content
                                        )
                                )
                        )
                ),
                "generationConfig", Map.of(
                        "temperature", 0.3
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            String response = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class)
                    .getBody();

            JsonNode root = objectMapper.readTree(response);
            String summary = root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            if (summary == null || summary.trim().length() < 50) {
                throw new RuntimeException("Gemini returned empty or invalid summary");
            }

            return summary.trim();

        } catch (RestClientException ex) {
            log.error("Gemini API call failed", ex);
            throw ex;
        }
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.GEMINI;
    }
}
