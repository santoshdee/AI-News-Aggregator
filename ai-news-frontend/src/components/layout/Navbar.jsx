import { Link } from "react-router-dom";
import { NavLink } from "react-router-dom";

function Navbar() {
  return (
    <nav className="sticky top-0 z-50 border-b border-slate-200 bg-white">
      <div className="
        font-serif 
        max-w-5xl 
        mx-auto 
        px-4 
        sm:px-6 
        lg:px-8 
        py-4
        flex 
        flex-col 
        md:flex-row 
        md:justify-between 
        md:items-center
        gap-3
      ">
        
        {/* Logo */}
        <Link 
          to="/" 
          className="text-xl sm:text-2xl font-bold text-slate-900"
        >
          NEWS BYTE
        </Link>

        {/* Category Navigation */}
        <div className="
          flex 
          gap-4 
          text-sm 
          sm:text-md
          text-slate-600 
          overflow-x-auto 
          whitespace-nowrap 
          pb-1
        ">
          <NavLink
            to="/"
            className={({ isActive }) =>
              isActive
                ? "text-indigo-600"
                : "hover:text-indigo-600"
            }
          >
            Latest
          </NavLink>

          <NavLink
            to="/category/technology"
            className={({ isActive }) =>
              isActive
                ? "text-indigo-600"
                : "hover:text-indigo-600"
            }
          >
            Technology
          </NavLink>

          <NavLink
            to="/category/science"
            className={({ isActive }) =>
              isActive
                ? "text-indigo-600"
                : "hover:text-indigo-600"
            }
          >
            Science
          </NavLink>

          <NavLink
            to="/category/world"
            className={({ isActive }) =>
              isActive
                ? "text-indigo-600"
                : "hover:text-indigo-600"
            }
          >
            World
          </NavLink>

          <NavLink
            to="/category/cricket"
            className={({ isActive }) =>
              isActive
                ? "text-indigo-600"
                : "hover:text-indigo-600"
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