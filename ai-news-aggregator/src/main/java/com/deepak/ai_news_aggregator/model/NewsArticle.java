package com.deepak.ai_news_aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "news_article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@CompoundIndexes({
        @CompoundIndex(
                name = "category_pubDate_idx",
                def = "{'category': 1, 'pubDate': -1}"
        ),
        @CompoundIndex(
                name = "source_pubDate_idx",
                def = "{'source': 1, 'pubDate': -1}"
        )
})
public class NewsArticle {
    @Id
    private String link; // this acts as both ID and actual article link

    private String title;
    private String description;  // we get from RSS feed
    private String content;      // full article content used to generate summary (scraped using Jsoup)

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date pubDate;

    private String category;
    private String source;  // website name
    private String summary; // ai-generated, initially null
}
