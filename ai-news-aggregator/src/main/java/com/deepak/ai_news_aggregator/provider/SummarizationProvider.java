package com.deepak.ai_news_aggregator.provider;

public interface SummarizationProvider {

    /**
     * Generates a summary for the given article content
     *
     * @param content full article text (JSOUP scraped)
     * @return summarized text
     * @throws Exception if summarization fails
     */
    String summarize(String content) throws Exception;

    /**
     * @return provider identifier (used for logging / debugging)
     */
    ProviderType getProviderType();
}
