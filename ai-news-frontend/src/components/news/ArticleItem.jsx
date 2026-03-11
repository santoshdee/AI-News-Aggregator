import { Link } from "react-router-dom";
import { formatDate } from "../../utils/formatDate";
import { motion } from "framer-motion";

export default function ArticleItem({ article }) {
  const formattedDate = formatDate(article.pubDate);

  const formattedTime = article.pubDate
    ? new Date(article.pubDate).toLocaleTimeString()
    : "";

  return (
    <motion.div
      whileHover={{ y: -3 }}
      transition={{ duration: 0.2 }}
      className="
        group 
        border-b 
        border-slate-200 
        py-5 
        sm:py-6
        transition-colors 
        duration-200 
        hover:bg-slate-100 
        px-2 
        -mx-2 
        rounded-md
      "
    >
      {/* Title */}
      <a
        href={article.link}
        target="_blank"
        rel="noopener noreferrer"
        className="
          font-serif 
          text-lg 
          sm:text-xl 
          md:text-[1.45rem]
          leading-snug 
          font-bold 
          text-slate-1000 
          group-hover:text-indigo-700 
          transition-colors
        "
      >
        {article.title}
      </a>

      {/* Summary */}
      {article.summary && (
        <p
          className="
            font-sans 
            text-slate-700 
            mt-3 
            sm:mt-4
            leading-relaxed 
            text-sm 
            sm:text-base 
            md:text-[1.05rem] 
            text-justify
          "
        >
          {article.summary}
        </p>
      )}

      {/* Meta */}
      <div
        className="
          font-sans 
          text-xs 
          sm:text-sm 
          text-slate-500 
          mt-3 
          sm:mt-4
          capitalize 
          flex 
          flex-wrap 
          gap-x-2 
          gap-y-1
        "
      >
        <span>{article.category}</span>
        <span>•</span>

        <Link
          to={`/source/${article.source}`}
          className="hover:text-indigo-600"
        >
          {article.source}
        </Link>

        <span>•</span>
        <span>{formattedDate}</span>

        <span>•</span>
        <span>{formattedTime}</span>
      </div>
    </motion.div>
  );
}
