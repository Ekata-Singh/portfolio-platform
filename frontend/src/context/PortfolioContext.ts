import { createContext } from 'react';
import type { PortfolioData } from '../types/portfolio';

export interface PortfolioContextType {
  data: PortfolioData | null;
  loading: boolean;
  error: string | null;
  refetch: () => Promise<void>;
}

export const PortfolioContext = createContext<PortfolioContextType | undefined>(
  undefined,
);
