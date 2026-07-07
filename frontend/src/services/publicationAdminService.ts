import api from './api';
import type { Publication } from '../types/portfolio';
import type { PublicationRequest } from '../types/requests';

export const createPublication = async (
  data: PublicationRequest,
): Promise<Publication> => {
  const response = await api.post<Publication>('/publication', data);
  return response.data;
};

export const updatePublication = async (
  id: number,
  data: PublicationRequest,
): Promise<Publication> => {
  const response = await api.put<Publication>(`/publication/${id}`, data);
  return response.data;
};

export const deletePublication = async (id: number): Promise<void> => {
  await api.delete(`/publication/${id}`);
};

export const uploadPublicationThumbnail = async (
  id: number,
  file: File,
): Promise<Publication> => {
  const formData = new FormData();
  formData.append('file', file);

  const response = await api.post<Publication>(`/publication/${id}/thumbnail`, formData);
  return response.data;
};
