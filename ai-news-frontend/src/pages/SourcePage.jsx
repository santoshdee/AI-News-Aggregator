import { useParams } from "react-router-dom";
import Navbar from "../components/layout/Navbar";
import Footer from "../components/layout/Footer";
import Container from "../components/layout/Container";

export default function SourcePage() {
  const { source } = useParams();

  return (
    <div className="bg-gray-50 min-h-screen flex flex-col">
      <Navbar />
      <Container>
        <h1 className="text-2xl font-semibold mt-8 mb-6 capitalize">
          {source} News
        </h1>
      </Container>
      <Footer />
    </div>
  );
}
