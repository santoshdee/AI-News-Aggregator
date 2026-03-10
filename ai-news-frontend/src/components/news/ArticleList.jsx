import { motion } from "framer-motion";
import ArticleItem from "./ArticleItem";

const containerVariants = {
  hidden: { opacity: 0 },
  visible: {
    opacity: 1,
    transition: {
      staggerChildren: 0.2,
    },
  },
};

const itemVariants = {
  hidden: { opacity: 0, y: 10 },
  visible: { opacity: 1, y: 0 },
};

export default function ArticleList({ articles = [] }) {
  return (
    // <div>
    //     {articles.map((article, index) => (
    //         <ArticleItem key={index} article={article}/>
    //     ))}
    // </div>

    <motion.div variants={containerVariants} initial="hidden" animate="visible">
      {articles.map((article, index) => (
        <motion.div key={index} variants={itemVariants}>
          <ArticleItem article={article} />
        </motion.div>
      ))}
    </motion.div>
  );
}
