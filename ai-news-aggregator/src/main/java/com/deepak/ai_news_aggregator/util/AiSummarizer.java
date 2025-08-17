package com.deepak.ai_news_aggregator.util;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Only focuses on one article at a time with
 * Simple exponential backoff retry
 * If gemini responds with 429 too many requests, it will:
 * Wait for a short delay (start with 1sec)
 * retry, doubling the wait time each time (max 3 retries)
 * if still failing, log and return an empty summary
 * */

@Slf4j
public class AiSummarizer {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("ai-news-aggregator")
            .load(); // loads .env file
    private static final String API_KEY = dotenv.get("GEMINI_API_KEY");
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;
    private static final WebClient webClient = WebClient.builder().build();

    public static String generateSummary(String articleContent) {
        if(articleContent == null || articleContent.isBlank()) return "";

        String prompt = "Summarize the article in 3–4 sentences, preserving only the key facts, events, and outcomes. Avoid opinions, filler words, or unrelated details. Keep it clear, concise, and true to the original meaning:\n\n" + articleContent;

        JSONObject requestBody = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject().put("text", prompt))
                                )
                        )
                );

        try {
            String response = webClient.post()
                    .uri(API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .onStatus(status -> status.value() == 429, clientResponse -> {
                        log.warn("Gemini API rate limit hit - throwing to trigger RabbitMQ retry queue.");
                        return Mono.error(new RuntimeException("429 Too many requests"));
                    })
                    .bodyToMono(String.class)
                    .block();

            JSONObject json = new JSONObject(response);
            JSONArray candidates = json.optJSONArray("candidates");

            if(candidates != null && !candidates.isEmpty()) {
                return candidates.getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text");
            }
        } catch (Exception e) {
            log.error("Error calling Gemini API: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return "";
    }
}
