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
@Order(1) // FIRST priority
@RequiredArgsConstructor
public class GroqSummarizationProvider implements SummarizationProvider{

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    @Value("${groq.base.url}")
    private String baseUrl;

    @Override
    public String summarize(String content) throws Exception {

        if(content == null || content.length() < 300) {
            throw new IllegalArgumentException("Content to short for summarization");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", "Summarize the following news article in 4-5 concise, neutral sentences. No opinions. No markdown."
                        ),
                        Map.of(
                                "role", "user",
                                "content", content
                        )
                ),
                "temperature", 0.3,
                "max_tokens", 300
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            String response = restTemplate
                    .exchange(baseUrl, HttpMethod.POST, request, String.class)
                    .getBody();

            JsonNode root = objectMapper.readTree(response);
            String summary =
                    root.path("choices").get(0).path("message").path("content").asText();

            if (summary == null || summary.trim().length() < 50) {
                throw new RuntimeException("Groq returned empty or invalid summary");
            }

            return summary.trim();

        } catch (RestClientException ex) {
            log.error("Groq API call failed", ex);
            throw ex;
        }
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.GROQ;
    }
}
