import api from './api';
import type { Contact } from '../types/portfolio';
import type { ContactRequest } from '../types/requests';

export const createContact = async (data: ContactRequest): Promise<Contact> => {
  const response = await api.post<Contact>('/contact', data);
  return response.data;
};

export const updateContact = async (
  id: number,
  data: ContactRequest,
): Promise<Contact> => {
  const response = await api.put<Contact>(`/contact/${id}`, data);
  return response.data;
};

export const deleteContact = async (id: number): Promise<void> => {
  await api.delete(`/contact/${id}`);
};
