package com.deepak.ai_news_aggregator.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SummarizationProviderRouter {

    private final List<SummarizationProvider> providers;

    public String summarizeWithFallback(String content) throws Exception {

        Exception lastException = null;

        for(SummarizationProvider provider: providers) {
            try {
                log.info("Attempting summarization using provider = {}", provider.getProviderType());
                return provider.summarize(content);
            } catch(Exception ex) {
                lastException = ex;
                log.warn(
                        "Summarization failed for provider = {}, trying next",
                        provider.getProviderType()
                );
            }
        }
        throw lastException != null
                ? lastException : new RuntimeException("All summarization providers failed");
    }
}
