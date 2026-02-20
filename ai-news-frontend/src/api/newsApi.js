import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

export const fetchLatestNews = async (page = 0) => {
    const response = await axios.get(
        `${API_BASE_URL}/news/latest?page=${page}`
    );
    return response.data;
};

export const fetchByCategory = async (category, page = 0) => {
    const response = await axios.get(
        `${API_BASE_URL}/news/category/${category}?page=${page}`
    );
    return response.data;
};

export const fetchBySource = async (source, page = 0) => {
    const response = await axios.get(
        `${API_BASE_URL}/news/source/${source}?page=${page}`
    );
    return response.data;
}