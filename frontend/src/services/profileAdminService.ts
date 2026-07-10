import api from './api';
import type { Profile } from '../types/portfolio';
import type { ProfileRequest } from '../types/requests';

export const getAllProfiles = async (): Promise<Profile[]> => {
  const response = await api.get<Profile[]>('/profile');
  return response.data;
};

export const createProfile = async (data: ProfileRequest): Promise<Profile> => {
  const response = await api.post<Profile>('/profile', data);
  return response.data;
};

export const updateProfile = async (
  id: number,
  data: ProfileRequest,
): Promise<Profile> => {
  const response = await api.put<Profile>(`/profile/${id}`, data);
  return response.data;
};

export const uploadProfileImage = async (id: number, file: File): Promise<Profile> => {
  const formData = new FormData();
  formData.append('file', file);

  const response = await api.post<Profile>(`/profile/${id}/image`, formData);
  return response.data;
};

export const uploadResume = async (id: number, file: File): Promise<Profile> => {
  const formData = new FormData();
  formData.append('file', file);

  const response = await api.post<Profile>(`/profile/${id}/resume`, formData);
  return response.data;
};
