import api from './api';
import type { Certification } from '../types/portfolio';
import type { CertificationRequest } from '../types/requests';

export const createCertification = async (
  data: CertificationRequest,
): Promise<Certification> => {
  const response = await api.post<Certification>('/certificate', data);
  return response.data;
};

export const updateCertification = async (
  id: number,
  data: CertificationRequest,
): Promise<Certification> => {
  const response = await api.put<Certification>(`/certificate/${id}`, data);
  return response.data;
};

export const deleteCertification = async (id: number): Promise<void> => {
  await api.delete(`/certificate/${id}`);
};

export const uploadCertificationThumbnail = async (
  id: number,
  file: File,
): Promise<Certification> => {
  const formData = new FormData();
  formData.append('file', file);

  const response = await api.post<Certification>(`/certificate/${id}/thumbnail`, formData);
  return response.data;
};
