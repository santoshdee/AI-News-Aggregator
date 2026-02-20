import { useParams, useSearchParams } from "react-router-dom";
import Navbar from "../components/layout/Navbar";
import Footer from "../components/layout/Footer";
import Container from "../components/layout/Container";
import ArticleList from "../components/news/ArticleList";
import Pagination from "../components/news/Pagination";
import useNews from "../hooks/useNews";

export default function SourcePage() {
  const { source } = useParams();
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) || 0;

  const {articles, pagination, loading, error} = useNews({
    type: "source",
    value: source,
    page
  });

  return (
    <div className="bg-gray-50 min-h-screen flex flex-col">
      <Navbar />
      <Container>
        <h1 className="text-2xl font-semibold mt-8 mb-6 capitalize">
          {source}
        </h1>

        {loading && <p className="text-gray-500">Loading...</p>}
        {error && <p className="text-red-500">{error}</p>}

        {!loading && !error && (
          <>
            <ArticleList articles={articles}/>
            <Pagination pagination={pagination}/>
          </>
        )}
      </Container>
      <Footer />
    </div>
  );
}
