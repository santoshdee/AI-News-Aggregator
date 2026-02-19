import ArticleItem from "./ArticleItem";

export default function ArticleList({ articles = [] }) {
    return (
        <div>
            {articles.map((article, index) => (
                <ArticleItem key={index} article={article}/>
            ))}
        </div>
    );
}