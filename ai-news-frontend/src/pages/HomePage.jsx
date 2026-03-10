import { useSearchParams } from "react-router-dom";
import Container from "../components/layout/Container";
import Footer from "../components/layout/Footer";
import Navbar from "../components/layout/Navbar";
import ArticleList from "../components/news/ArticleList";
import useNews from "../hooks/useNews";
import Pagination from "../components/news/Pagination";
import Loader from "../components/ui/Loader";
import EmptyState from "../components/ui/EmptyState";

export default function HomePage() {
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) || 0;

  const { articles, pagination, loading, error } = useNews({
    type: "latest",
    value: null,
    page,
  });

  return (
    <div className="font-sans bg-slate-50 min-h-screen flex flex-col">
      <Navbar />
      <Container>
        <h1 className="font-serif text-2xl text-center font-semibold mt-8 mb-6">
          Latest News
        </h1>

        {loading && <Loader />}
        {error && <p className="text-red-500">{error}</p>}

        {!loading &&
          !error &&
          (articles.length === 0 ? (
            <EmptyState />
          ) : (
            <>
              <ArticleList articles={articles} />
              <Pagination pagination={pagination} />
            </>
          ))}
      </Container>
      <Footer />
    </div>
  );
}
