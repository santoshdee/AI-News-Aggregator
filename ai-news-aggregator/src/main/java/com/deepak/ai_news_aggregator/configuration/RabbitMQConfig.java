package com.deepak.ai_news_aggregator.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "summarization_queue";
    public static final String RETRY_QUEUE_NAME = "summarization_retry_queue";
    public static final String EXCHANGE_NAME = "news_exchange";
    public static final String ROUTING_KEY = "news.summarize";
    public static final String RETRY_ROUTING_KEY = "news.summarize.retry";

    // main queue
    @Bean
    public Queue mainQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-max-priority", 10) // priorities 0-10
                .build();
    }

    // retry queue with 24hr TTL and dead-letter back to main exchange
    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(RETRY_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY)
                .withArgument("x-message-ttl", 24*60*60*1000) // 5 minutes
                .build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding mainBinding(Queue mainQueue, TopicExchange exchange) {
        return BindingBuilder.bind(mainQueue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding retryBinding(Queue retryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(retryQueue).to(exchange).with(RETRY_ROUTING_KEY);
    }
}
