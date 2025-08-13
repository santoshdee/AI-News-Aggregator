package com.deepak.ai_news_aggregator.controller;

import com.deepak.ai_news_aggregator.repository.NewsArticleRepository;
import com.deepak.ai_news_aggregator.dto.NewsFilterRequest;
import com.deepak.ai_news_aggregator.dto.NewsResponse;
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

    @GetMapping("/latest")
    public ResponseEntity<NewsResponse> getLatestNews() {
        List<NewsArticle> articles = newsArticleRepository.findAllByOrderByPubDateDesc();

        if(articles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    NewsResponse.builder()
                            .message("No content fetched")
                            .articles(Collections.emptyList())
                            .build()
            ); // 204 No content
        } else {
            return ResponseEntity.ok(
                    NewsResponse.builder()
                            .message("Fetched " + articles.size() + " articles from available sources")
                            .articles(articles)
                            .build()
            );
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<NewsResponse> getNewsByCategory(@PathVariable String category) {
        if(category == null || category.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    NewsResponse.builder()
                            .message("Category cannot be empty")
                            .build()
            );
        }

        String normalizedCategory = category.trim().toLowerCase();
        if(!ALLOWED_CATEGORIES.contains(normalizedCategory)) {
            return ResponseEntity.badRequest().body(
                    NewsResponse.builder()
                            .message("Invalid source. Allowed categories: " + ALLOWED_CATEGORIES)
                            .articles(Collections.emptyList())
                            .build()
            );
        }

        List<NewsArticle> articles = newsArticleRepository.findByCategoryOrderByPubDateDesc(normalizedCategory);
        if(articles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    NewsResponse.builder()
                            .message("No news articles found for category: " + normalizedCategory)
                            .articles(Collections.emptyList())
                            .build()
            );
        }
        return ResponseEntity.ok(
                NewsResponse.builder()
                        .message("Fetched " + articles.size() + " articles from source: " + normalizedCategory)
                        .articles(articles)
                        .build()
        );
    }

    @GetMapping("/source/{source}")
    public ResponseEntity<NewsResponse> getNewsBySource(@PathVariable String source) {
        if(source == null || source.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    NewsResponse.builder()
                            .message("Source cannot be empty")
                            .build()
            );
        }

        String normalizedSource = source.trim().toLowerCase();
        if(!ALLOWED_SOURCES.contains(normalizedSource)) {
            return ResponseEntity.badRequest().body(
                    NewsResponse.builder()
                            .message("Invalid source. Allowed sources: " + ALLOWED_SOURCES)
                            .articles(Collections.emptyList())
                            .build()
            );
        }

        List<NewsArticle> articles = newsArticleRepository.findBySourceOrderByPubDateDesc(normalizedSource);
        if(articles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    NewsResponse.builder()
                            .message("No news articles found for source: " + normalizedSource)
                            .articles(Collections.emptyList())
                            .build()
            );
        }

        return ResponseEntity.ok(
                NewsResponse.builder()
                        .message("Fetched " + articles.size() + " articles from source: " + normalizedSource)
                        .articles(articles)
                        .build()
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<NewsResponse> filterNews(@RequestBody NewsFilterRequest filterRequest) {
        try {
            Query query = new Query();
            List<Criteria> criteriaList = new ArrayList<>();

            // keyword search in title or description (case-insensitive)
            if(filterRequest.getKeyword() != null && !filterRequest.getKeyword().isBlank()) {
                String keyword = filterRequest.getKeyword();
                Criteria keywordCriteria = new Criteria().orOperator(
                        Criteria.where("title").regex(keyword, "i"),
                        Criteria.where("description").regex(keyword, "i")
                );
                criteriaList.add(keywordCriteria);
            }

            // category (case-insensitive)
            if(filterRequest.getCategory() != null && !filterRequest.getCategory().isBlank()) {
                criteriaList.add(Criteria.where("category").regex("^" + filterRequest.getCategory() + "$", "i"));
            }

            // source (case-insensitive)
            if(filterRequest.getSource() != null && !filterRequest.getSource().isBlank()) {
                criteriaList.add(Criteria.where("source").regex("^" + filterRequest.getSource() + "$", "i"));
            }

            // date range
            if(filterRequest.getStartDate() != null || filterRequest.getEndDate() != null) {
                Criteria dateCriteria = Criteria.where("pubDate");

                if(filterRequest.getStartDate() != null) {
                    Date fromDate = dateFormat.parse(filterRequest.getStartDate());
                    dateCriteria = dateCriteria.gte(fromDate);
                }

                if(filterRequest.getEndDate() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateFormat.parse(filterRequest.getEndDate()));
                    calendar.add(Calendar.DATE, 1); // make endDate inclusive
                    Date toDate = calendar.getTime();
                    dateCriteria = dateCriteria.lt(toDate);
                }

                criteriaList.add(dateCriteria);
            }

            if(!criteriaList.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
            }

            query.with(Sort.by(Sort.Direction.DESC, "pubDate"));
            List<NewsArticle> results = mongoTemplate.find(query, NewsArticle.class);

            if(results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        NewsResponse.builder()
                                .message("No news articles match the given filters")
                                .articles(Collections.emptyList())
                                .build()
                );
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    NewsResponse.builder()
                            .message("Filtered news articles retrieved successfully")
                            .articles(results)
                            .build()
            );

        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    NewsResponse.builder()
                            .message("Invalid date format. Please use yyyy-MM-dd")
                            .articles(Collections.emptyList())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    NewsResponse.builder()
                            .message("Something went wrong while filtering news")
                            .articles(Collections.emptyList())
                            .build()
            );
        }
    }
}


