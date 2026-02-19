import { useParams } from "react-router-dom";
import Navbar from "../components/layout/Navbar";
import Container from "../components/layout/Container";
import Footer from "../components/layout/Footer";

export default function CategoryPage() {
  const { category } = useParams();

  return (
    <div className="bg-gray-50 min-h-screen flex flex-col">
      <Navbar />
      <Container>
        <h1 className="text-2xl font-semibold mt-8 mb-6 capitalize">
          {category} News
        </h1>
      </Container>
      <Footer />
    </div>
  );
}
