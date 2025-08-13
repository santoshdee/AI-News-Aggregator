package com.deepak.ai_news_aggregator.service;

import com.deepak.ai_news_aggregator.model.NewsArticle;
import com.deepak.ai_news_aggregator.repository.NewsArticleRepository;
import com.deepak.ai_news_aggregator.util.ArticleExtractor;
import com.deepak.ai_news_aggregator.util.ArticleStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.deepak.ai_news_aggregator.util.NewsConstants.RSS_FEEDS;

@Slf4j
@Component
public class NewsFetcherService {

    private final NewsArticleRepository newsArticleRepository;
    private final SummarizationProducer summarizationProducer;

    public NewsFetcherService(NewsArticleRepository newsArticleRepository,
                              SummarizationProducer summarizationProducer) {
        this.newsArticleRepository = newsArticleRepository;
        this.summarizationProducer = summarizationProducer;
    }

    @Scheduled(fixedRate = 300*1000)  // every 5 min for testing
    public void fetchRssNews() {

        RSS_FEEDS.forEach((meta, url) -> {
            String[] parts = meta.split("\\|");
            String category = parts[0];
            String source = parts[1];

            log.info("Fetching feed from:  [{} | {}]", category, source);

            URI uri = URI.create(url);
            try {
                InputStream stream = uri.toURL().openStream();
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
                NodeList items = doc.getElementsByTagName("item");

                for(int i=0; i< items.getLength(); i++) {
                    Element item = (Element)items.item(i);
                    String title = item.getElementsByTagName("title").item(0).getTextContent();
                    String link = item.getElementsByTagName("link").item(0).getTextContent();
                    String description = item.getElementsByTagName("description").item(0).getTextContent();

                    String pubDateStr = item.getElementsByTagName("pubDate").item(0).getTextContent();
                    Date pubDate;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                        pubDate = formatter.parse(pubDateStr);
                    } catch (Exception e) {
                        log.error("Failed to parse pubDate: {}. Skipping article.", pubDateStr);
                        continue;
                    }

                    // skip if already exists in DB
                    if(newsArticleRepository.existsById(link)) {
                        log.debug("Article already exists: {}", link);
                        continue;
                    }

                    String articleText = ArticleExtractor.fetchFullArticle(link);

                    NewsArticle article = NewsArticle.builder()
                            .link(link)
                            .title(title)
                            .description(description)
                            .content(articleText)
                            .pubDate(pubDate)
                            .category(category)
                            .source(source)
                            .status(ArticleStatus.PENDING) // latest addition to tackle article summaries
                            .build();

                    newsArticleRepository.save(article);
                    summarizationProducer.sendArticleForSummarization(link, true); // send to RabbitMQ, new article, high priority
                    log.info("Article saved: {} [{} | {}]", title, category, source);
                }

            } catch (Exception e) {
                log.error("Error parsing feed from {}: {}", source, e.getMessage());
            }
        });
    }
}
