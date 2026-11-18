const rawOrigin = import.meta.env.VITE_API_ORIGIN ?? 'http://localhost:8080';

// Tolerate a trailing slash in the env var so we never build "//api/v1" URLs.
export const API_ORIGIN = rawOrigin.replace(/\/+$/, '');
export const API_BASE_URL = `${API_ORIGIN}/api/v1`;
