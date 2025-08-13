package com.deepak.ai_news_aggregator.util;

import com.deepak.ai_news_aggregator.dto.PaginatedNewsResponse;
import com.deepak.ai_news_aggregator.model.NewsArticle;
import org.springframework.data.domain.Page;

public class PaginationUtil {
    public static PaginatedNewsResponse fromPage(Page<NewsArticle> page) {
        return PaginatedNewsResponse.builder()
                .articles(page.getContent())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .pageSize(page.getSize())
                .build();
    }
}
