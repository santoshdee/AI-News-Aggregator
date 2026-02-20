import { Link } from "react-router-dom";

export default function ArticleItem({ article }) {
  const formattedDate = article.pubDate
    ? new Date(article.pubDate).toLocaleDateString()
    : "";

  const formattedTime = article.pubDate
    ? new Date(article.pubDate).toLocaleTimeString()
    : "";

  return (
    <div className="border-b border-gray-200 py-4">
      <a
        href={article.link}
        target="_blank"
        rel="noopener noreferrer"
        className="text-xl font-semibold text-gray-900 hover:text-blue-600 transition-colors"
      >
        {article.title}
      </a>

      {article.summary && (
        <p className="text-gray-600 mt-2">{article.summary}</p>
      )}

      <div className="text-sm text-gray-500 mt-4 captialize space-x-2">
        {/* {article.category} • {article.source} • {formattedDate} • {formattedTime} */}
        <span>{article.category}</span>
        <span>•</span>
        <Link
          to={`/source/${article.source}`}
          className="hover:text-blue-600"
        >
          {article.source}
        </Link>
        <span>•</span>
        <span>{formattedDate}</span>
        <span>•</span>
        <span>{formattedTime}</span>
      </div>
    </div>
  );
}
