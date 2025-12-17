package com.deepak.ai_news_aggregator.controller;

import com.deepak.ai_news_aggregator.dto.NewsPageResponse;
import com.deepak.ai_news_aggregator.repository.NewsArticleRepository;
import com.deepak.ai_news_aggregator.dto.NewsFilterRequest;
import com.deepak.ai_news_aggregator.model.NewsArticle;
import com.deepak.ai_news_aggregator.util.PaginationUtil;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

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

    /// --------------------- LATEST ------------------------------

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PaginationUtil.createPageable(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "pubDate")
        );

        Page<NewsArticle> articlePage = newsArticleRepository.findAllByOrderByPubDateDesc(pageable);

        if(articlePage.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(
                NewsPageResponse.builder()
                        .message("Fetched latest news articles")
                        .articles(articlePage.getContent())
                        .page(articlePage.getNumber())
                        .size(articlePage.getSize())
                        .totalElements(articlePage.getTotalElements())
                        .totalPages(articlePage.getTotalPages())
                        .hasNext(articlePage.hasNext())
                        .hasPrevious(articlePage.hasPrevious())
                        .build()
        );
    }

    /// --------------------- CATEGORY ------------------------------

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getNewsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // validation
        if(category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }

        String normalizedCategory = category.trim().toLowerCase();
        if(!ALLOWED_CATEGORIES.contains(normalizedCategory)) {
            throw new IllegalArgumentException(
                    "Invalid category. Allowed categories: " + ALLOWED_CATEGORIES
            );
        }

        Pageable pageable = PaginationUtil.createPageable(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "pubDate")
        );

        Page<NewsArticle> articlePage = newsArticleRepository.findByCategoryOrderByPubDateDesc(
                normalizedCategory,
                pageable
        );

        if(articlePage.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(
                NewsPageResponse.builder()
                        .message("Fetched news articles for category: " + normalizedCategory)
                        .articles(articlePage.getContent())
                        .page(articlePage.getNumber())
                        .size(articlePage.getSize())
                        .totalElements(articlePage.getTotalElements())
                        .totalPages(articlePage.getTotalPages())
                        .hasNext(articlePage.hasNext())
                        .hasPrevious(articlePage.hasPrevious())
                        .build()
        );
    }

    /// --------------------- SOURCE ------------------------------

    @GetMapping("/source/{source}")
    public ResponseEntity<?> getNewsBySource(
            @PathVariable String source,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // validation
        if(source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be empty");
        }

        String normalizedSource = source.trim().toLowerCase();
        if(!ALLOWED_SOURCES.contains(normalizedSource)) {
            throw new IllegalArgumentException(
                    "Invalid source. Allowed sources: " + ALLOWED_SOURCES
            );
        }

        Pageable pageable = PaginationUtil.createPageable(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "pubDate")
        );

        Page<NewsArticle> articlePage = newsArticleRepository.findBySourceOrderByPubDateDesc(
                normalizedSource,
                pageable
        );

        if(articlePage.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(
                NewsPageResponse.builder()
                        .message("Fetched news articles for source: " + normalizedSource)
                        .articles(articlePage.getContent())
                        .page(articlePage.getNumber())
                        .size(articlePage.getSize())
                        .totalElements(articlePage.getTotalElements())
                        .totalPages(articlePage.getTotalPages())
                        .hasNext(articlePage.hasNext())
                        .hasPrevious(articlePage.hasPrevious())
                        .build()
        );
    }

    /// --------------------- FILTER ------------------------------

    @PostMapping("/filter")
    public ResponseEntity<?> filterNews(
            @RequestBody(required = false) NewsFilterRequest filterRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws ParseException {

        // body validation
        if (filterRequest == null) { // rare - explicitly when client sends 'null'
            throw new IllegalArgumentException("Filter request body is required");
        }

        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        // keyword search in title or content (case-insensitive)
        if (filterRequest.getKeyword() != null && !filterRequest.getKeyword().isBlank()) {
            String keyword = Pattern.quote(filterRequest.getKeyword().trim());

            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("title").regex(keyword, "i"),
                    Criteria.where("content").regex(keyword, "i")
            );
            criteriaList.add(keywordCriteria);
        }

        // category (case-insensitive)
        if (filterRequest.getCategory() != null && !filterRequest.getCategory().isBlank()) {
            String category = filterRequest.getCategory().trim().toLowerCase();
            criteriaList.add(Criteria.where("category").regex("^" + Pattern.quote(category) + "$", "i"));
        }

        // source (case-insensitive)
        if (filterRequest.getSource() != null && !filterRequest.getSource().isBlank()) {
            String source = filterRequest.getSource().trim().toLowerCase();
            criteriaList.add(Criteria.where("source").regex("^" + Pattern.quote(source) + "$", "i"));
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

        // pagination
        Pageable pageable = PaginationUtil.createPageable(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "pubDate")
        );
        query.with(pageable);

        // fetch paginated articles
        List<NewsArticle> articles = mongoTemplate.find(query, NewsArticle.class);

        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        // total count
        long totalElements = mongoTemplate.count(query.skip(0).limit(0), NewsArticle.class);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return ResponseEntity.ok(
                NewsPageResponse.builder()
                        .message("Filtered articles fetched successfully")
                        .articles(articles)
                        .page(page)
                        .size(size)
                        .totalElements(totalElements)
                        .totalPages(totalPages)
                        .hasNext((page + 1) * size < totalElements)
                        .hasPrevious(page > 0)
                        .build()
        );
    }
}


