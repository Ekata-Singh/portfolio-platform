import api from './api';
import type { Education } from '../types/portfolio';
import type { EducationRequest } from '../types/requests';

export const createEducation = async (data: EducationRequest): Promise<Education> => {
  const response = await api.post<Education>('/education', data);
  return response.data;
};

export const updateEducation = async (
  id: number,
  data: EducationRequest,
): Promise<Education> => {
  const response = await api.put<Education>(`/education/${id}`, data);
  return response.data;
};

export const deleteEducation = async (id: number): Promise<void> => {
  await api.delete(`/education/${id}`);
};
