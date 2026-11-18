import axios from 'axios';
import api from './api';
import type { PortfolioData } from '../types/portfolio';

const MAX_ATTEMPTS = 3;

const isRetryable = (error: unknown) =>
  axios.isAxiosError(error) &&
  (!error.response || error.response.status >= 500);

/**
 * Free-tier hosts put the API to sleep when idle; the first request can fail or take
 * up to a minute while it wakes up. Retry a couple of times before giving up.
 */
export const getPortfolio = async (): Promise<PortfolioData> => {
  let lastError: unknown;

  for (let attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
    try {
      const response = await api.get<PortfolioData>('/portfolio');
      return response.data;
    } catch (error) {
      lastError = error;
      if (!isRetryable(error) || attempt === MAX_ATTEMPTS) {
        break;
      }
      await new Promise((resolve) => setTimeout(resolve, 3000 * attempt));
    }
  }

  throw lastError;
};
