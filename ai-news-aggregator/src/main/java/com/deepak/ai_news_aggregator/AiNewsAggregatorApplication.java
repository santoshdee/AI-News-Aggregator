package com.deepak.ai_news_aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AiNewsAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiNewsAggregatorApplication.class, args);
	}

}
