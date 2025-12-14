package com.deepak.ai_news_aggregator.repository;

import com.deepak.ai_news_aggregator.model.NewsArticle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NewsArticleRepository extends MongoRepository<NewsArticle, String> {
    boolean existsById(String link);
    List<NewsArticle> findAllByOrderByPubDateDesc();
    List<NewsArticle> findByCategoryOrderByPubDateDesc(String category);
    List<NewsArticle> findBySourceOrderByPubDateDesc(String source);
}
