package com.deepak.ai_news_aggregator.util;

import java.util.*;

public class NewsConstants {
    /**
     * wrap it with Collections.unmodifiableSet to make it immutable and prevent accidental modifications at runtime.
     */
    public static final Set<String> ALLOWED_CATEGORIES = Set.of("science", "technology", "world", "cricket");

    public static final Set<String> ALLOWED_SOURCES = Set.of("techcrunch.com", "sciencedaily.com", "indianexpress.com", "hindustantimes.com");


    /**
    * Consider making RSS_FEEDs a Map<CategorySourcePair, String> if you want type safety
    * But for now, current Map<String, String> with category|source as key is fine and easy to parse
    *
    */

    public static final Map<String, String> RSS_FEEDS = Map.ofEntries(
            Map.entry("technology|techcrunch.com", "https://techcrunch.com/feed/"),
            Map.entry("science|sciencedaily.com", "https://www.sciencedaily.com/rss/top/science.xml"),
            Map.entry("world|indianexpress.com", "https://indianexpress.com/section/world/feed/"),
            Map.entry("cricket|hindustantimes.com", "https://www.hindustantimes.com/feeds/rss/cricket/rssfeed.xml")
    );

    private NewsConstants() {
        // private constructor to prevent instantiation
    }
}
