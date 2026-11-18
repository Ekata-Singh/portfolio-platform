import { Link } from 'react-router-dom';

export function NotFound() {
  return (
    <div className="section not-found-page">
      <p className="not-found-code">404</p>
      <h1>Page not found</h1>
      <p>The page you are looking for doesn&apos;t exist or has moved.</p>
      <Link to="/" className="hero-resume-button">
        Back to home
      </Link>
    </div>
  );
}
