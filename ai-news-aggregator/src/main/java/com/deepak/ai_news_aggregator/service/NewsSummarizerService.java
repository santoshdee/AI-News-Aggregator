package com.deepak.ai_news_aggregator.service;

import com.deepak.ai_news_aggregator.model.NewsArticle;
import com.deepak.ai_news_aggregator.repository.NewsArticleRepository;
import com.deepak.ai_news_aggregator.util.AiSummarizer;
import com.deepak.ai_news_aggregator.util.ArticleStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * NewsService that pulls the top N pending articles and summarizes them
 * Processes PENDING first (up to 8 per cycle)
 * Then retries FAILED if there's room left
 */

@Slf4j
@Component
public class NewsSummarizerService {
//    private final NewsArticleRepository newsArticleRepository;
//    public NewsSummarizerService(NewsArticleRepository newsArticleRepository) {
//        this.newsArticleRepository = newsArticleRepository;
//    }
//
//    @Scheduled(fixedRate = 180_000, initialDelay = 60_000) // start 60s after fetcher, run every 180s
//    public void summarizePendingArticles() {
//        final int MAX_SUMMARIES = 8;
//        int processed = 0;
//
//        // process PENDING first
//        List<NewsArticle> pendingArticles = newsArticleRepository.findTop8ByStatusOrderByPubDateAsc(ArticleStatus.PENDING);
//        processed += summarizeArticles(pendingArticles, MAX_SUMMARIES - processed);
//
//        // retry FAILED if there's capacity left
//        if(processed < MAX_SUMMARIES) {
//            List<NewsArticle> failedArticles = newsArticleRepository.findTop8ByStatusOrderByPubDateAsc(ArticleStatus.FAILED);
//            processed += summarizeArticles(failedArticles, MAX_SUMMARIES - processed);
//        }
//
//        log.info("Summarization cycle complete. Total processed: {}", processed);
//    }
//
//    private int summarizeArticles(List<NewsArticle> articles, int limit) {
//        int count = 0; // we return this, as number of articles processed
//        for(NewsArticle article: articles) {
//            if(count >= limit) break;
//
//            try {
//               String summary = AiSummarizer.generateSummary(article.getContent());
//               if(summary != null && !summary.isBlank()) {
//                   article.setSummary(summary);
//                   article.setStatus(ArticleStatus.DONE);
//               } else {
//                   article.setStatus(ArticleStatus.FAILED);
//               }
//            } catch (Exception e) {
//                article.setStatus(ArticleStatus.FAILED);
//                log.error("Error summarizing article {}: {}", article.getLink(), e.getMessage());
//            }
//
//            newsArticleRepository.save(article);
//            count++;
//        }
//        return count;
//    }
}
