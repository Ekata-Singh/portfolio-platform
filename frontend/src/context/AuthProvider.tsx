import { useState, type ReactNode } from 'react';
import type { AuthUser, LoginRequest } from '../types/auth';
import { login as loginRequest } from '../services/authService';
import { AuthContext } from './AuthContext';

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthUser | null>(() => {
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');
    return username && role ? { username, role } : null;
  });

  const [token, setToken] = useState<string | null>(() =>
    localStorage.getItem('token'),
  );

  const login = async (credentials: LoginRequest) => {
    const response = await loginRequest(credentials);

    localStorage.setItem('token', response.token);
    localStorage.setItem('username', response.username);
    localStorage.setItem('role', response.role);

    setToken(response.token);
    setUser({ username: response.username, role: response.role });
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');

    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{ user, token, isAuthenticated: !!token, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
}
