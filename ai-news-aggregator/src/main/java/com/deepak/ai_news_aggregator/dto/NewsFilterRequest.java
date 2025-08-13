/**
* This is a POJO class used for binding the JSON request body
* For the endpoint /news/filter
*/

package com.deepak.ai_news_aggregator.dto;

import lombok.Data;

@Data
public class NewsFilterRequest {
    private String category;
    private String source;
    private String keyword;
    private String startDate;   // format: yyyy-MM-dd
    private String endDate;     // format: yyyy-MM-dd
}
