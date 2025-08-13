package com.deepak.ai_news_aggregator.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
public class ArticleExtractor {
    public static String fetchFullArticle(String url) {
        try {
            // connect and fetch html
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                    .timeout(5000)
                    .get();

            // select all <p> tags (common for main content)
            Elements paragraphs = doc.select("p");
            StringBuilder text = new StringBuilder();

            for(Element p: paragraphs) {
                String line = p.text().trim();
                if(line.length() > 30) {
                   text.append(line).append("\n\n");
                }
            }

            // return combined article text
            return text.toString().trim();

        } catch(IOException e) {
            log.error("Error fetching article: {}", e.getMessage());
            return "";
        }
    }
}
