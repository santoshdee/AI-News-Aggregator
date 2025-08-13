package com.deepak.ai_news_aggregator.service;

import com.deepak.ai_news_aggregator.configuration.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * We send link(article's ID) to the queue after saving a new article in the fetcher
 * */

@Service
@RequiredArgsConstructor
public class SummarizationProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendArticleForSummarization(String link, boolean isNewArticle) {
        int priority = isNewArticle ? 10 : 1; // new articles gets the highest priority
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                link,
                message -> {
                    message.getMessageProperties().setPriority(priority);
                    return message;
                }
        );
    }

    // add a retry send method for sending messages to the retry queue
    public void sendToRetryQueue(String link) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.RETRY_ROUTING_KEY,
                link
        );
    }
}
