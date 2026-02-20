/**
 * page starts from 0 (Spring default)
 * UI page 1  -> backend page 0
 * UI page 2  -> backend page 1
 */

import { useNavigate, useSearchParams } from "react-router-dom";

export default function Pagination({ pagination }) {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const currentPage = Number(searchParams.get("page")) || 0;

  const { totalPages, hasNext, hasPrevious } = pagination;

  if (totalPages <= 1) return null;

  const goToPage = (page) => {
    navigate(`?page=${page}`);
    window.scrollTo({ top: 0, behaviour: "smooth" });
  };

  const generatePages = () => {
    const pages = [];
    const windowSize = 2; // show 2 pages before & after current

    const start = Math.max(0, currentPage - windowSize);
    const end = Math.min(totalPages - 1, currentPage + windowSize);

    // always show first page
    if (start > 0) {
      pages.push(0);
      if (start > 1) pages.push("left-ellipsis");
    }

    for (let i = start; i <= end; i++) {
      pages.push(i);
    }

    // always show last page
    if (end < totalPages - 1) {
      if (end < totalPages - 2) pages.push("right-ellipsis");
      pages.push(totalPages - 1);
    }

    return pages;
  };

  const pages = generatePages();

  return (
    <div className="flex justify-center items-center gap-4 mt-10 mb-6 text-sm">
      {/* Previous */}
      <button
        onClick={() => goToPage(currentPage - 1)}
        disabled={!hasPrevious}
        className={`px-3 py-1 border rounded ${
          !hasPrevious
            ? "text-gray-400 border-gray-200 cursor-not-allowed"
            : "hover:bg-gray-100"
        }`}
      >
        Prev
      </button>

      {/* Page Numbers */}
      {pages.map((page, index) => {
        if (page === "left-ellipsis" || page === "right-ellipsis") {
          return (
            <span key={index} className="px-2 text-gray-500">
              ...
            </span>
          );
        }

        return (
          <button
            key={index}
            onClick={() => goToPage(page)}
            className={`px-3 py-1 rounded ${
              currentPage === page
                ? "bg-blue-600 text-white"
                : "hover:bg-gray-100"
            }`}
          >
            {page + 1}
          </button>
        );
      })}

      {/* Next */}
      <button
        onClick={() => goToPage(currentPage + 1)}
        disabled={!hasNext}
        className={`px-3 py-1 border rounded ${
          !hasNext
            ? "text-gray-400 border-gray-200 cursor-not-allowed"
            : "hover:bg-gray-100"
        }`}
      >
        Next
      </button>
    </div>
  );
}
