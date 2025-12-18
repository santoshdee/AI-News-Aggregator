package com.deepak.ai_news_aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SummarizationScheduler {

    private final SummarizationService summarizationService;

    @Scheduled(fixedDelay = 2 * 60 * 1000) // runs every 2 mins
    public void runSummarizationJob() {
        log.info("AI Summarization job started");

        summarizationService.summarizePendingArticles();

        log.info("AI summarization job finished");
    }
}
