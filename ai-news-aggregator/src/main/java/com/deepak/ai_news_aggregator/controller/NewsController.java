package com.deepak.ai_news_aggregator.controller;

import com.deepak.ai_news_aggregator.dto.NewsSuccessResponse;
import com.deepak.ai_news_aggregator.repository.NewsArticleRepository;
import com.deepak.ai_news_aggregator.dto.NewsFilterRequest;
import com.deepak.ai_news_aggregator.model.NewsArticle;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.deepak.ai_news_aggregator.util.NewsConstants.ALLOWED_CATEGORIES;
import static com.deepak.ai_news_aggregator.util.NewsConstants.ALLOWED_SOURCES;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final NewsArticleRepository newsArticleRepository;
    private final MongoTemplate mongoTemplate;

    public NewsController(NewsArticleRepository newsArticleRepository, MongoTemplate mongoTemplate) {
        this.newsArticleRepository = newsArticleRepository;
        this.mongoTemplate = mongoTemplate;
    }

    // --------------------- LATEST ------------------------------

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestNews() {
        List<NewsArticle> articles = newsArticleRepository.findAllByOrderByPubDateDesc();

        if(articles.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(
                NewsSuccessResponse.builder()
                        .message("Fetched " + articles.size() + " latest articles")
                        .articles(articles)
                        .build()
        );
    }

    // --------------------- CATEGORY ------------------------------

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getNewsByCategory(@PathVariable String category) {
        if(category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }

        String normalizedCategory = category.trim().toLowerCase();
        if(!ALLOWED_CATEGORIES.contains(normalizedCategory)) {
            throw new IllegalArgumentException(
                    "Invalid category. Allowed categories: " + ALLOWED_CATEGORIES
            );
        }

        List<NewsArticle> articles = newsArticleRepository.findByCategoryOrderByPubDateDesc(normalizedCategory);
        if(articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(
                NewsSuccessResponse.builder()
                        .message("Fetched " + articles.size() + " articles from category: " + normalizedCategory)
                        .articles(articles)
                        .build()
        );
    }

    // --------------------- SOURCE ------------------------------

    @GetMapping("/source/{source}")
    public ResponseEntity<?> getNewsBySource(@PathVariable String source) {
        if(source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be empty");
        }

        String normalizedSource = source.trim().toLowerCase();
        if(!ALLOWED_SOURCES.contains(normalizedSource)) {
            throw new IllegalArgumentException(
                    "Invalid source. Allowed sources: " + ALLOWED_SOURCES
            );
        }

        List<NewsArticle> articles = newsArticleRepository.findBySourceOrderByPubDateDesc(normalizedSource);
        if(articles.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(
                NewsSuccessResponse.builder()
                        .message("Fetched " + articles.size() + " articles from source: " + normalizedSource)
                        .articles(articles)
                        .build()
        );
    }

    // --------------------- FILTER ------------------------------
    @PostMapping("/filter")
    public ResponseEntity<?> filterNews(@RequestBody(required = false) NewsFilterRequest filterRequest) throws ParseException {
        if (filterRequest == null) { // rare - explicitly when client sends 'null'
            throw new IllegalArgumentException("Filter request body is required");
        }

        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        // keyword search in title or content (case-insensitive)
        if (filterRequest.getKeyword() != null && !filterRequest.getKeyword().isBlank()) {
            String keyword = filterRequest.getKeyword();
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("title").regex(keyword, "i"),
                    Criteria.where("content").regex(keyword, "i")
            );
            criteriaList.add(keywordCriteria);
        }

        // category (case-insensitive)
        if (filterRequest.getCategory() != null && !filterRequest.getCategory().isBlank()) {
            criteriaList.add(Criteria.where("category").regex("^" + filterRequest.getCategory() + "$", "i"));
        }

        // source (case-insensitive)
        if (filterRequest.getSource() != null && !filterRequest.getSource().isBlank()) {
            criteriaList.add(Criteria.where("source").regex("^" + filterRequest.getSource() + "$", "i"));
        }

        // date range
        if (filterRequest.getStartDate() != null || filterRequest.getEndDate() != null) {
            Criteria dateCriteria = Criteria.where("pubDate");

            if (filterRequest.getStartDate() != null) {
                Date fromDate = dateFormat.parse(filterRequest.getStartDate());
                dateCriteria = dateCriteria.gte(fromDate);
            }

            if (filterRequest.getEndDate() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormat.parse(filterRequest.getEndDate()));
                calendar.add(Calendar.DATE, 1); // make endDate inclusive
                Date toDate = calendar.getTime();
                dateCriteria = dateCriteria.lt(toDate);
            }
            criteriaList.add(dateCriteria);
        }

        if (criteriaList.isEmpty()) {
           throw new IllegalArgumentException("At least one filter must be provided");
        }

        query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));

        query.with(Sort.by(Sort.Direction.DESC, "pubDate"));
        List<NewsArticle> results = mongoTemplate.find(query, NewsArticle.class);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                NewsSuccessResponse.builder()
                        .message(results.size() + " Filtered news articles retrieved successfully")
                        .articles(results)
                        .build()
        );
    }
}


