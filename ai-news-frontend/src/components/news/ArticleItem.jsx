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

      <div className="text-sm text-gray-500 mt-4 captialize">
        {article.category} • {article.source} • {formattedDate} • {formattedTime}
      </div>
    </div>
  );
}
