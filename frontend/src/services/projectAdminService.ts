import api from './api';
import type { Project } from '../types/portfolio';
import type { ProjectRequest } from '../types/requests';

export const createProject = async (data: ProjectRequest): Promise<Project> => {
  const response = await api.post<Project>('/project', data);
  return response.data;
};

export const updateProject = async (
  id: number,
  data: ProjectRequest,
): Promise<Project> => {
  const response = await api.put<Project>(`/project/${id}`, data);
  return response.data;
};

export const deleteProject = async (id: number): Promise<void> => {
  await api.delete(`/project/${id}`);
};

export const uploadProjectThumbnail = async (id: number, file: File): Promise<Project> => {
  const formData = new FormData();
  formData.append('file', file);

  const response = await api.post<Project>(`/project/${id}/thumbnail`, formData);
  return response.data;
};
