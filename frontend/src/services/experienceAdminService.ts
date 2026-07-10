import api from './api';
import type { Experience } from '../types/portfolio';
import type { ExperienceRequest } from '../types/requests';

export const createExperience = async (data: ExperienceRequest): Promise<Experience> => {
  const response = await api.post<Experience>('/experience', data);
  return response.data;
};

export const updateExperience = async (
  id: number,
  data: ExperienceRequest,
): Promise<Experience> => {
  const response = await api.put<Experience>(`/experience/${id}`, data);
  return response.data;
};

export const deleteExperience = async (id: number): Promise<void> => {
  await api.delete(`/experience/${id}`);
};
