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
public class NewsResponse {
    private String message;
    private List<NewsArticle> articles;
}
