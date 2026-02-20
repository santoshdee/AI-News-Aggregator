import { Link } from "react-router-dom";
import { NavLink } from "react-router-dom";

function Navbar() {
  return (
    <nav className="sticky top-0 z-50 border-b border-gray-200 bg-white">
      <div className="max-w-4xl mx-auto px-6 py-4 flex justify-between items-center">
        <Link to="/" className="text-xl font-bold text-gray-900">
          News Byte
        </Link>

        <div className="space-x-6 text-sm text-gray-600">
          <NavLink
            to="/"
            className={({ isActive }) =>
              isActive ? "text-blue-600 font-medium" : "hover:text-blue-600"
            }
          >
            Latest
          </NavLink>

          <NavLink
            to="/category/technology"
            className={({ isActive }) =>
              isActive ? "text-blue-600 font-medium" : "hover:text-blue-600"
            }
          >
            Technology
          </NavLink>
          <NavLink
            to="/category/science"
            className={({ isActive }) =>
              isActive ? "text-blue-600 font-medium" : "hover:text-blue-600"
            }
          >
            Science
          </NavLink>
          <NavLink
            to="/category/world"
            className={({ isActive }) =>
              isActive ? "text-blue-600 font-medium" : "hover:text-blue-600"
            }
          >
            World
          </NavLink>
          <NavLink
            to="/category/cricket"
            className={({ isActive }) =>
              isActive ? "text-blue-600 font-medium" : "hover:text-blue-600"
            }
          >
            Cricket
          </NavLink>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;