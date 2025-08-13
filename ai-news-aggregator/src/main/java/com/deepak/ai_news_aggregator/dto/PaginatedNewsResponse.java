package com.deepak.ai_news_aggregator.dto;

import com.deepak.ai_news_aggregator.model.NewsArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedNewsResponse {
    private List<NewsArticle> articles;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
}
