import { useEffect, useState } from "react";
import { fetchByCategory, fetchBySource, fetchLatestNews } from "../api/newsApi";

export default function useNews({ type, value, page }) {
    const [articles, setArticles] = useState([]);
    const [pagination, setPagination] = useState({
        page: 0,
        size: 10,
        totalPages: 0,
        totalElements: 0,
        hasNext: false,
        hasPrevious: false
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadNews = async () => {
            try {
                setLoading(true);
                setError(null);

                let data;
                if(type === "category") {
                    data = await fetchByCategory(value, page);
                } else if(type === "source") {
                    data = await fetchBySource(value, page);
                } else {
                    data = await fetchLatestNews(page);
                }

                setArticles(data.articles);
                setPagination({
                    page: data.page,
                    size: data.size,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    hasNext: data.hasNext,
                    hasPrevious: data.hasPrevious
                });
            } catch (err) {
                setError("Failed to fetch news ", err);
            } finally {
                setLoading(false);
            }
        };

        loadNews();
    }, [type, value, page]);

    return {articles, pagination, loading, error};
}