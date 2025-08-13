package com.deepak.ai_news_aggregator.service;

import com.deepak.ai_news_aggregator.configuration.RabbitMQConfig;
import com.deepak.ai_news_aggregator.model.NewsArticle;
import com.deepak.ai_news_aggregator.repository.NewsArticleRepository;
import com.deepak.ai_news_aggregator.util.AiSummarizer;
import com.deepak.ai_news_aggregator.util.ArticleStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SummarizationConsumer {
    private final NewsArticleRepository newsArticleRepository;
    private final SummarizationProducer summarizationProducer;

    public SummarizationConsumer(NewsArticleRepository newsArticleRepository, SummarizationProducer summarizationProducer) {
        this.newsArticleRepository = newsArticleRepository;
        this.summarizationProducer = summarizationProducer;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, concurrency = "1")
    public void receiveMessage(String link) {
        NewsArticle article = newsArticleRepository.findById(link).orElse(null);
        if(article == null || article.getStatus() == ArticleStatus.DONE) return;

        try {
            String summary = AiSummarizer.generateSummary(article.getContent());
            if(summary != null && !summary.isBlank()) {
                article.setSummary(summary);
                article.setStatus(ArticleStatus.DONE);
            } else {
                article.setStatus(ArticleStatus.FAILED);
            }
        } catch (RuntimeException e) {
            if(e.getMessage() != null && e.getMessage().contains("429")) {
                log.warn("Rate limit hit for article {}. Sending to retry queue for next day.", link);
                summarizationProducer.sendToRetryQueue(link);
                return; // skip saving as FAILED now
            }
            article.setStatus(ArticleStatus.FAILED);
            log.error("Error summarizing article {}: {}", link, e.getMessage());
        }
        newsArticleRepository.save(article);

        // throttle Gemini API requests to ~200/day
        try {
            Thread.sleep(7 * 60 * 1000); // 7 mins between calls
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
