import api from './api';
import type { Achievement } from '../types/portfolio';
import type { AchievementRequest } from '../types/requests';

export const createAchievement = async (
  data: AchievementRequest,
): Promise<Achievement> => {
  const response = await api.post<Achievement>('/achievement', data);
  return response.data;
};

export const updateAchievement = async (
  id: number,
  data: AchievementRequest,
): Promise<Achievement> => {
  const response = await api.put<Achievement>(`/achievement/${id}`, data);
  return response.data;
};

export const deleteAchievement = async (id: number): Promise<void> => {
  await api.delete(`/achievement/${id}`);
};

export const uploadAchievementCertificate = async (
  id: number,
  file: File,
): Promise<Achievement> => {
  const formData = new FormData();
  formData.append('file', file);

  const response = await api.post<Achievement>(`/achievement/${id}/certificate`, formData);
  return response.data;
};
