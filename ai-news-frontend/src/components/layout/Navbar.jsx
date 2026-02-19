import { Link } from "react-router-dom";

function Navbar() {
    return (
        <nav className="border-b border-gray-200 bg-white">
            <div className="max-w-4xl mx-auto px-6 py-4 flex justify-between items-center">
                <Link to="/" className="text-xl font-bold text-gray-900">
                    News Byte
                </Link>

                <div className="space-x-6 text-sm text-gray-600">
                    <Link to="/" className="hover:text-blue-600">Latest</Link>
                    <Link to="/category/technology" className="hover:text-blue-600">Technology</Link>
                    <Link to="/category/science" className="hover:text-blue-600">Science</Link>
                    <Link to="/category/world" className="hover:text-blue-600">World</Link>
                    <Link to="/category/cricket" className="hover:text-blue-600">Cricket</Link>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;