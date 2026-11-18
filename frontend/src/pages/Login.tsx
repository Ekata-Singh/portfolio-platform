import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { LoginForm } from '../components/LoginForm';

export function Login() {
  const navigate = useNavigate();
  const [params] = useSearchParams();
  const expired = params.get('expired') === '1';

  return (
    <div className="login-page">
      <LoginForm
        notice={expired ? 'Your session has expired. Please sign in again.' : undefined}
        onSuccess={() => navigate('/')}
      />
      <Link to="/" className="login-back-link">
        &larr; Back to portfolio
      </Link>
    </div>
  );
}
