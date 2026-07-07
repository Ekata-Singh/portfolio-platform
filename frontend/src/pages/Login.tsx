import { useNavigate } from 'react-router-dom';
import { LoginForm } from '../components/LoginForm';

export function Login() {
  const navigate = useNavigate();

  return (
    <div className="login-page">
      <LoginForm onSuccess={() => navigate('/')} />
    </div>
  );
}
