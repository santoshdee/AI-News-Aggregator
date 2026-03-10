import { Link } from "react-router-dom";
import { NavLink } from "react-router-dom";

function Navbar() {
  return (
    <nav className="sticky top-0 z-50 border-b border-slate-200 bg-white">
      <div className="font-serif max-w-5xl mx-auto px-6 py-4 flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold text-slate-1000">
          NEWS BYTE
        </Link>

        <div className="space-x-6 text-md text-slate-600">
          <NavLink
            to="/"
            className={({ isActive }) =>
              isActive ? "text-indigo-600 border-indigo-600" : "hover:text-indigo-600"
          }
          >
            Latest
          </NavLink>

          <NavLink
            to="/category/technology"
            className={({ isActive }) =>
              isActive ? "text-indigo-600 border-indigo-600" : "hover:text-indigo-600"
            }
          >
            Technology
          </NavLink>

          <NavLink
            to="/category/science"
            className={({ isActive }) =>
              isActive ? "text-indigo-600 border-indigo-600" : "hover:text-indigo-600"
            }
          >
            Science
          </NavLink>

          <NavLink
            to="/category/world"
            className={({ isActive }) =>
              isActive ? "text-indigo-600 border-indigo-600" : "hover:text-indigo-600"
            }
          >
            World
          </NavLink>

          <NavLink
            to="/category/cricket"
            className={({ isActive }) =>
              isActive ? "text-indigo-600 border-indigo-600" : "hover:text-indigo-600"
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