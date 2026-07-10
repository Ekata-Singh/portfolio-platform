import { useCallback, useEffect, useState, type ReactNode } from 'react';
import type { PortfolioData } from '../types/portfolio';
import { getPortfolio } from '../services/portfolioService';
import { PortfolioContext } from './PortfolioContext';

export function PortfolioProvider({ children }: { children: ReactNode }) {
  const [data, setData] = useState<PortfolioData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchPortfolio = useCallback(async () => {
    try {
      const result = await getPortfolio();
      setData(result);
      setError(null);
    } catch {
      setError('Failed to load portfolio data. Please try again later.');
    }
  }, []);

  useEffect(() => {
    fetchPortfolio().finally(() => setLoading(false));
  }, [fetchPortfolio]);

  return (
    <PortfolioContext.Provider value={{ data, loading, error, refetch: fetchPortfolio }}>
      {children}
    </PortfolioContext.Provider>
  );
}
