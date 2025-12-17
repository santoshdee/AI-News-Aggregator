package com.deepak.ai_news_aggregator.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {

    private static final int MAX_PAGE_SIZE = 50;

    private PaginationUtil() {
        // prevent instantiation
    }

    public static Pageable createPageable(int page, int size, Sort sort) {

        if(page < 0) { // -ve page numbers will not exist and page numbering start from 0 by default
            throw new IllegalArgumentException("Page number must be >= 0");
        }

        if(size <= 0 || size > MAX_PAGE_SIZE) { // each page size at most 50
            throw new IllegalArgumentException("Page size must be between 1 and " + MAX_PAGE_SIZE);
        }

        return PageRequest.of(page, size, sort);
    }
}
