import api from './api';
import type { Skill } from '../types/portfolio';
import type { SkillRequest } from '../types/requests';

export const createSkill = async (data: SkillRequest): Promise<Skill> => {
  const response = await api.post<Skill>('/skill', data);
  return response.data;
};

export const updateSkill = async (id: number, data: SkillRequest): Promise<Skill> => {
  const response = await api.put<Skill>(`/skill/${id}`, data);
  return response.data;
};

export const deleteSkill = async (id: number): Promise<void> => {
  await api.delete(`/skill/${id}`);
};
