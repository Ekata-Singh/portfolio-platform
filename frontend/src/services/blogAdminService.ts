import api from './api';
import type { Blog } from '../types/portfolio';
import type { BlogRequest } from '../types/requests';

export const createBlog = async (data: BlogRequest): Promise<Blog> => {
  const response = await api.post<Blog>('/blog', data);
  return response.data;
};

export const updateBlog = async (id: number, data: BlogRequest): Promise<Blog> => {
  const response = await api.put<Blog>(`/blog/${id}`, data);
  return response.data;
};

export const deleteBlog = async (id: number): Promise<void> => {
  await api.delete(`/blog/${id}`);
};
