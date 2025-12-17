package com.deepak.ai_news_aggregator.dto;

import com.deepak.ai_news_aggregator.model.NewsArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class NewsPageResponse {
    private String message;
    private List<NewsArticle> articles;

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
