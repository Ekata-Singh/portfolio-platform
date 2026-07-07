import { Outlet } from 'react-router-dom';
import { Navbar } from '../components/Navbar';
import { Footer } from '../components/Footer';
import { usePortfolio } from '../hooks/usePortfolio';

export function PublicLayout() {
  const { loading, error } = usePortfolio();

  return (
    <div className="public-layout">
      <Navbar />
      <main className="public-content">
        {loading && <p className="status-message">Loading...</p>}
        {error && <p className="status-message status-error">{error}</p>}
        {!loading && !error && <Outlet />}
      </main>
      <Footer />
    </div>
  );
}
