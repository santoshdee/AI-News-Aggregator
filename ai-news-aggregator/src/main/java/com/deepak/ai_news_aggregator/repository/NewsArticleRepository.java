package com.deepak.ai_news_aggregator.repository;

import com.deepak.ai_news_aggregator.model.NewsArticle;
import com.deepak.ai_news_aggregator.util.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NewsArticleRepository extends MongoRepository<NewsArticle, String> {
    boolean existsById(String link);
    List<NewsArticle> findAllByOrderByPubDateDesc();
    List<NewsArticle> findByCategoryOrderByPubDateDesc(String category);
    List<NewsArticle> findBySourceOrderByPubDateDesc(String source);
    List<NewsArticle> findTop8ByStatusOrderByPubDateAsc(ArticleStatus status);

    Page<NewsArticle> findAll(Pageable pageable);
    Page<NewsArticle> findByCategoryIgnoreCase(String category, Pageable pageable);
    Page<NewsArticle> findBySourceIgnoreCase(String source, Pageable pageable);
}
