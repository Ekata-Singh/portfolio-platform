import api from './api';
import type { Technology } from '../types/portfolio';
import type { TechnologyRequest } from '../types/requests';
import type { PageResponse } from '../types/page';

export interface TechnologyQueryParams {
  query?: string;
  page?: number;
  size?: number;
  sortBy?: string;
  direction?: string;
}

export const getTechnologiesPage = async (
  params: TechnologyQueryParams,
): Promise<PageResponse<Technology>> => {
  const response = await api.get<PageResponse<Technology>>('/technology/page', { params });
  return response.data;
};

export const createTechnology = async (data: TechnologyRequest): Promise<Technology> => {
  const response = await api.post<Technology>('/technology', data);
  return response.data;
};

export const updateTechnology = async (
  id: number,
  data: TechnologyRequest,
): Promise<Technology> => {
  const response = await api.put<Technology>(`/technology/${id}`, data);
  return response.data;
};

export const deleteTechnology = async (id: number): Promise<void> => {
  await api.delete(`/technology/${id}`);
};
