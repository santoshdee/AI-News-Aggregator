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

import java.util.Map;

@Slf4j
@Component
@Order(3) // LAST FALLBACK
@RequiredArgsConstructor
public class HuggingFaceSummarizationProvider implements SummarizationProvider{

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${hf.api.key}")
    private String apiKey;

    @Value("${hf.model}")
    private String model;

    @Value("${hf.base.url}")
    private String baseUrl;

    @Override
    public String summarize(String content) throws Exception {

        if(content == null || content.length() < 300) {
            throw new IllegalArgumentException("Content too short for summarization");
        }

        String url = baseUrl + "/" + model;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "inputs", content,
                "parameters", Map.of(
                        "max_length", 150,
                        "min_length", 60,
                        "do_sample", false
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            String response = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class)
                    .getBody();

            JsonNode root = objectMapper.readTree(response);

            // HF usually returns an array
            String summary = root.get(0).path("summary_text").asText();

            if (summary == null || summary.trim().length() < 50) {
                throw new RuntimeException("Hugging Face returned empty or invalid summary");
            }

            return summary.trim();

        } catch (RestClientException ex) {
            log.error("Hugging Face API call failed", ex);
            throw ex;
        }
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.HUGGING_FACE;
    }
}
