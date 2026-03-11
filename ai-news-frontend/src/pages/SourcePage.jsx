import { useParams, useSearchParams } from "react-router-dom";
import Navbar from "../components/layout/Navbar";
import Footer from "../components/layout/Footer";
import Container from "../components/layout/Container";
import ArticleList from "../components/news/ArticleList";
import Pagination from "../components/news/Pagination";
import useNews from "../hooks/useNews";
import Loader from "../components/ui/Loader";
import EmptyState from "../components/ui/EmptyState";

export default function SourcePage() {
  const { source } = useParams();
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) || 0;

  const { articles, pagination, loading, error } = useNews({
    type: "source",
    value: source,
    page,
  });

  return (
    <div className="bg-slate-50 min-h-screen flex flex-col pb-10">
      <Navbar />
      <Container>
        <h1 className="font-serif text-xl sm:text-2xl text-center font-semibold mt-6 sm:mt-8 mb-5 sm:mb-6 capitalize">
          {source}
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
