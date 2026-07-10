import { useState, type FormEvent } from 'react';
import axios from 'axios';
import type { ApiErrorResponse } from '../types/api';
import { usePortfolio } from './usePortfolio';

interface CrudService<TItem, TRequest> {
  create: (data: TRequest) => Promise<TItem>;
  update: (id: number, data: TRequest) => Promise<TItem>;
  remove: (id: number) => Promise<void>;
}

interface UseInlineCrudOptions<TItem, TRequest extends Record<string, unknown>> {
  service: CrudService<TItem, TRequest>;
  emptyForm: TRequest;
  toFormValues: (item: TItem) => TRequest;
  getId: (item: TItem) => number;
  getDisplayName: (item: TItem) => string;
}

export function useInlineCrud<TItem, TRequest extends Record<string, unknown>>({
  service,
  emptyForm,
  toFormValues,
  getId,
  getDisplayName,
}: UseInlineCrudOptions<TItem, TRequest>) {
  const { refetch } = usePortfolio();

  const [editingId, setEditingId] = useState<number | null>(null);
  const [isAdding, setIsAdding] = useState(false);
  const [form, setForm] = useState<TRequest>(emptyForm);
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});
  const [submitting, setSubmitting] = useState(false);

  const startEdit = (item: TItem) => {
    setIsAdding(false);
    setEditingId(getId(item));
    setForm(toFormValues(item));
    setFieldErrors({});
  };

  const startAdd = () => {
    setEditingId(null);
    setIsAdding(true);
    setForm(emptyForm);
    setFieldErrors({});
  };

  const cancel = () => {
    setEditingId(null);
    setIsAdding(false);
    setFieldErrors({});
  };

  const handleChange = (name: keyof TRequest, value: string | number | boolean) => {
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setSubmitting(true);
    setFieldErrors({});

    try {
      if (editingId) {
        await service.update(editingId, form);
      } else {
        await service.create(form);
      }

      cancel();
      await refetch();
    } catch (err) {
      if (axios.isAxiosError<ApiErrorResponse>(err) && err.response?.data) {
        const data = err.response.data;
        setFieldErrors(data.validationErrors ?? { _general: data.message });
      } else {
        setFieldErrors({ _general: 'Something went wrong. Please try again.' });
      }
    } finally {
      setSubmitting(false);
    }
  };

  const handleDelete = async (item: TItem) => {
    if (!window.confirm(`Delete "${getDisplayName(item)}"?`)) {
      return;
    }

    await service.remove(getId(item));
    await refetch();
  };

  return {
    editingId,
    isAdding,
    form,
    fieldErrors,
    submitting,
    startEdit,
    startAdd,
    cancel,
    handleChange,
    handleSubmit,
    handleDelete,
  };
}
