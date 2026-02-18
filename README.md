# AI-Powered News Aggregator

An intelligent backend system that aggregates news from mutliple RSS feeds, extracts full article content, and generates AI-powered summaries using a resilient multi-provider fallback architecture.

Built with Spring Boot + MongoDB, featuring asynchronous background processing, rate-limit handling, and multi-model AI orchestration

---

## Architecture Overview
1. **RSS Ingestion Layer**
   - Fetches articles from external RSS feeds
   - Extracts full article content using JSOUP
   - Stores articles in MongoDB with `summary = null`
2. **Background Summarization Pipeline**
   - Runs via Spring `@Scheduled`
   - Process articles in small batches (latest-first)
   - Applies exponential backoff for retries
   - Handles AI provider failures gracefully
3. **AI Provider Orchestration**
   Implements deterministic fallback:
   - Groq (Primary - low latency)
   - Gemini (Secondary)
   - Hugging Face (Final fallback)
   If one provider fails (e.g., 429 rate limit), the system automatically switches to the next      provider

## Key Features
- AI-powered article summarization
- Multi-provider fallback (Groq, Gemini, Hugging Face)
- Exponential backoff retry strategy
- Rate-limit resilience (HTTP 429 handling)
- Asynchronous background processing
- Latest-first prioritization
- Pagination-optimized REST APIs
- Clean separation of concerns
- Fault-tolerant architecture

---

## Tech Stack
- **Backend**
  - Java 21
  - Spring Boot 3.x
  - Spring Scheduling
  - Spring Data MongoDB
  - Rest APIs
- **Database**
  - MongoDB
    - Flexible schema
    - Efficient storage for RSS articles
- **AI Providers**
  - Groq
  - Gemini API
  - Hugging Face Inference Router
- **Content Extraction**
  - JSOUP (HTML parsing and article content scraping)
- Utilities & Tools
  - Lombok
  - Jackson (JSON parsing)
  - RestTemplate (HTTP client)
 
---

## Summarization Flow
1. Article saved with summary = null
2. Scheduled job runs every 2 minutes
3. Fetches latest unsummarized articles
4. Applies backoff eligibility logic
5. Calls AI provider router
6. On success -> saves summary
7. On failure -> increments retry count

---

## Scalability Considerations
- Batch-based processing prevents API flooding
- Provider abstraction allows easy addition of new AI models
- Deterministic fallback ensures summary generation despite failures
- Ready to evolve into queue-based architecture (Kafka / RabbitMQ)

