import { useState } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { usePortfolio } from '../hooks/usePortfolio';
import { useAuth } from '../hooks/useAuth';
import { useTheme } from '../hooks/useTheme';
import { LoginModal } from './LoginModal';

const beforeBlogs = [
  { to: '/', label: 'Home' },
  { to: '/#about', label: 'About' },
  { to: '/#education', label: 'Education' },
  { to: '/#experience', label: 'Experience' },
  { to: '/#projects', label: 'Projects' },
];

const afterBlogs = [
  { to: '/#achievements', label: 'Achievements' },
  { to: '/#publications', label: 'Publications' },
  { to: '/#certificates', label: 'Certificates' },
];

export function Navbar() {
  const { data } = usePortfolio();
  const { isAuthenticated, logout } = useAuth();
  const { theme, toggleTheme } = useTheme();
  const [isOpen, setIsOpen] = useState(false);
  const [showLogin, setShowLogin] = useState(false);
  const brandName = data?.profile?.fullName ?? 'Portfolio';
  const closeMenu = () => setIsOpen(false);

  const handleLogout = () => {
    logout();
    window.location.href = '/';
  };

  return (
    <nav className="navbar">
      <div className="navbar-row">
        <Link to="/" className="nav-brand" onClick={closeMenu}>
          {brandName}
        </Link>

        <button
          type="button"
          className="nav-toggle"
          onClick={() => setIsOpen((prev) => !prev)}
          aria-label="Toggle navigation menu"
          aria-expanded={isOpen}
        >
          <span />
          <span />
          <span />
        </button>
      </div>

      <div className={isOpen ? 'nav-links open' : 'nav-links'}>
        {beforeBlogs.map((link) => (
          <Link key={link.to} to={link.to} className="nav-link" onClick={closeMenu}>
            {link.label}
          </Link>
        ))}

        <NavLink
          to="/blogs"
          className={({ isActive }) => (isActive ? 'nav-link active' : 'nav-link')}
          onClick={closeMenu}
        >
          Blogs
        </NavLink>

        {afterBlogs.map((link) => (
          <Link key={link.to} to={link.to} className="nav-link" onClick={closeMenu}>
            {link.label}
          </Link>
        ))}

        <button
          type="button"
          className="theme-toggle-button"
          onClick={toggleTheme}
          aria-label={theme === 'dark' ? 'Switch to light mode' : 'Switch to dark mode'}
        >
          {theme === 'dark' ? '☀' : '☽'}
        </button>

        {isAuthenticated ? (
          <div className="nav-auth-group">
            <span className="editing-badge">Editing</span>
            <button type="button" className="nav-text-button" onClick={handleLogout}>
              Logout
            </button>
          </div>
        ) : (
          <button
            type="button"
            className="nav-admin-link"
            onClick={() => {
              setShowLogin(true);
              closeMenu();
            }}
          >
            Admin
          </button>
        )}
      </div>

      {showLogin && <LoginModal onClose={() => setShowLogin(false)} />}
    </nav>
  );
}
