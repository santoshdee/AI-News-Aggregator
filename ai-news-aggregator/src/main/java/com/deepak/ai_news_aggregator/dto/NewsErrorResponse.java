package com.deepak.ai_news_aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class NewsErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
    private Instant timestamp;
}
