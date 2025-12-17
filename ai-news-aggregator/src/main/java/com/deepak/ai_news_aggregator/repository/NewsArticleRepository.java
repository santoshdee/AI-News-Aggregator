package com.deepak.ai_news_aggregator.repository;

import com.deepak.ai_news_aggregator.model.NewsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NewsArticleRepository extends MongoRepository<NewsArticle, String> {
    boolean existsById(String link);

    // pagination
    Page<NewsArticle> findAllByOrderByPubDateDesc(Pageable pageable);
    Page<NewsArticle> findByCategoryOrderByPubDateDesc(
            String category,
            Pageable pageable
    );
    Page<NewsArticle> findBySourceOrderByPubDateDesc(
            String source,
            Pageable pageable
    );
}
