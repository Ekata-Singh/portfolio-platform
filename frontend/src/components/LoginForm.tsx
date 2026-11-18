import { useState, type FormEvent } from 'react';
import axios from 'axios';
import { useAuth } from '../hooks/useAuth';
import '../pages/Login.css';

interface LoginFormProps {
  onSuccess?: () => void;
  notice?: string;
}

export function LoginForm({ onSuccess, notice }: LoginFormProps) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const { login } = useAuth();

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setError(null);
    setLoading(true);

    try {
      await login({ username, password });
      onSuccess?.();
    } catch (err) {
      if (axios.isAxiosError(err) && err.response?.status === 401) {
        setError('Invalid username or password');
      } else if (axios.isAxiosError(err) && !err.response) {
        setError('Cannot reach the server. It may be waking up — please try again in a moment.');
      } else {
        setError('Something went wrong. Please try again.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="login-form">
      <h1>Admin Login</h1>

      {notice && !error && <p className="login-notice">{notice}</p>}
      {error && <p className="login-error">{error}</p>}

      <label htmlFor="username">Username</label>
      <input
        id="username"
        type="text"
        value={username}
        autoComplete="username"
        onChange={(event) => setUsername(event.target.value)}
        required
      />

      <label htmlFor="password">Password</label>
      <input
        id="password"
        type="password"
        value={password}
        autoComplete="current-password"
        onChange={(event) => setPassword(event.target.value)}
        required
      />

      <button type="submit" disabled={loading}>
        {loading ? 'Signing in...' : 'Sign In'}
      </button>
    </form>
  );
}
