import { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Navbar } from '../components/Navbar';
import { Footer } from '../components/Footer';
import { usePortfolio } from '../hooks/usePortfolio';

function LoadingState() {
  const [slow, setSlow] = useState(false);

  useEffect(() => {
    const timer = window.setTimeout(() => setSlow(true), 4000);
    return () => window.clearTimeout(timer);
  }, []);

  return (
    <div className="status-message" role="status" aria-live="polite">
      <span className="loading-spinner" aria-hidden="true" />
      <p>Loading portfolio…</p>
      {slow && (
        <p className="status-hint">
          The server sleeps when idle and is waking up. This can take up to a minute on the
          first visit.
        </p>
      )}
    </div>
  );
}

export function PublicLayout() {
  const { data, loading, error, retry } = usePortfolio();
  const name = data?.profile?.fullName;
  const headline = data?.profile?.headline;

  useEffect(() => {
    if (name) {
      document.title = headline ? `${name} — ${headline}` : `${name} — Portfolio`;
    }
  }, [name, headline]);

  return (
    <div className="public-layout">
      <a href="#main-content" className="skip-link">
        Skip to content
      </a>
      <Navbar />
      <main className="public-content" id="main-content">
        {loading && <LoadingState />}
        {!loading && error && (
          <div className="status-message status-error" role="alert">
            <p>{error}</p>
            <button type="button" className="hero-resume-button" onClick={retry}>
              Try again
            </button>
          </div>
        )}
        {!loading && !error && <Outlet />}
      </main>
      <Footer />
    </div>
  );
}
