import { useEffect } from 'react';
import { LoginForm } from './LoginForm';

interface LoginModalProps {
  onClose: () => void;
}

export function LoginModal({ onClose }: LoginModalProps) {
  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') {
        onClose();
      }
    };

    document.addEventListener('keydown', handleKeyDown);
    return () => document.removeEventListener('keydown', handleKeyDown);
  }, [onClose]);

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div
        className="modal-panel"
        onClick={(event) => event.stopPropagation()}
        role="dialog"
        aria-modal="true"
      >
        <button
          type="button"
          className="modal-close"
          onClick={onClose}
          aria-label="Close login"
        >
          &times;
        </button>

        <LoginForm onSuccess={onClose} />
      </div>
    </div>
  );
}
