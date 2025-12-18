package com.deepak.ai_news_aggregator.service;

import com.deepak.ai_news_aggregator.model.NewsArticle;
import com.deepak.ai_news_aggregator.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummarizationService {

    private static final int BATCH_SIZE = 5;
    private static final int MAX_RETRIES = 5;

    private final NewsArticleRepository newsArticleRepository;

    public void summarizePendingArticles() {
        log.info("Fetching articles pending summarization");

        List<NewsArticle> articles = newsArticleRepository.findBySummaryIsNullOrderByPubDateDesc(
                PageRequest.of(0, BATCH_SIZE)
        );

        log.info("Found {} candidate articles", articles.size());

        Instant now = Instant.now();

        for(NewsArticle article: articles) {
            if(!isEligibleForRetry(article, now)) {
                log.debug(
                        "Skipping article id = {} due to backoff (retryCount = {})",
                        article.getLink(),
                        article.getRetryCount()
                );
                continue;
            }

            // mark attempt time before AI call
            article.setLastSummaryAttemptAt(now);
            newsArticleRepository.save(article);

            log.info(
                    "Article id={} is eligible for summarization (retryCount={})",
                    article.getLink(),
                    article.getRetryCount()
            );

            // Next step here: call AI for summarization
        }
    }

    private Boolean isEligibleForRetry(NewsArticle article, Instant now) {

        if(article.getRetryCount() >= MAX_RETRIES) {
            return false;
        }

        Instant lastAttempt = article.getLastSummaryAttemptAt();

        if(lastAttempt == null) {
            return true; // never attempted, article can go for summarization
        }

        long backoffMinutes = (long) Math.pow(2, article.getRetryCount());
        Duration elapsed = Duration.between(lastAttempt, now);

        return elapsed.toMinutes() >= backoffMinutes;
    }
}
