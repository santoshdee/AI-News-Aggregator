import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "../pages/HomePage";
import CategoryPage from "../pages/CategoryPage";
import SourcePage from "../pages/SourcePage";
import Footer from "../components/layout/Footer";
import Navbar from "../components/layout/Navbar";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/category/:category" element={<CategoryPage />} />
          <Route path="/source/:source" element={<SourcePage />} />
        </Routes>
    </BrowserRouter>
  );
}
