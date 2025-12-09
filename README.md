# AI-Powered News Aggregator with Summarization

An intelligent **Spring Boot** application that fetches articles from multiple **RSS feeds**, stores them in **MongoDB**, and generates concise summaries using **Google Gemini API**.  
The system is designed with **scalable architecture**, supports **pagination** for efficient browsing, and demonstrates a modern AI-powered workflow for content aggregation.

---

## Features
- **RSS Feed Integration**: Fetches latest news articles from sources like *TechCrunch, ScienceDaily, IndianExpress, HindustanTimes*.
- **HTML Parsing with Jsoup**: Extracts clean article text from full web pages.
- **AI Summarization Workflow**: Uses **Spring WebClient** to send articles to **Gemini API** and generate summaries.
- **MongoDB Storage**: Stores articles with fields like title, description, publication date, source, category, and AI summary.
- **REST API Endpoints**:  
  - `GET /news/latest` → Fetch latest articles 
  - `GET /news/category/{category}` → Fetch articles by category  
  - `GET /news/source/{source}` → Fetch articles by source  
  - `POST /news/filter` → Flexible filtering with JSON body  
- **Pagination Utility**: Reusable `PaginationUtil` ensures consistent pagination across endpoints.
- **Scheduled Fetching**: Periodically pulls and stores fresh content from configured RSS feeds.

---

## Tech Stack
- **Backend**: Spring Boot 3.x (Java 21)
- **Database**: MongoDB (NoSQL, document storage)
- **Libraries & Tools**:  
  - `Jsoup` → HTML parsing & article text extraction  
  - `Spring WebClient` → Asynchronous external API calls  
  - `Spring Data MongoDB` → Repository support  
  - `Lombok` → Boilerplate reduction  
- **AI Integration**: Google **Gemini API** for summarization
- **Build Tool**: Maven
