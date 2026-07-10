import api from './api';
import type { PortfolioData } from '../types/portfolio';

export const getPortfolio = async (): Promise<PortfolioData> => {
  const response = await api.get<PortfolioData>('/portfolio');
  return response.data;
};
