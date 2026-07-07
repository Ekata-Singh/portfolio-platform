import { useState, type FormEvent } from 'react';
import axios from 'axios';
import { useAuth } from '../../hooks/useAuth';
import { usePortfolio } from '../../hooks/usePortfolio';
import { updateProfile } from '../../services/profileAdminService';
import type { Profile } from '../../types/portfolio';
import type { ApiErrorResponse } from '../../types/api';

interface AboutSectionProps {
  profile: Profile | null | undefined;
}

export function AboutSection({ profile }: AboutSectionProps) {
  const { isAuthenticated } = useAuth();
  const { refetch } = usePortfolio();

  const [isEditing, setIsEditing] = useState(false);
  const [draft, setDraft] = useState(profile?.about ?? '');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const startEdit = () => {
    setDraft(profile?.about ?? '');
    setError(null);
    setIsEditing(true);
  };

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();

    if (!profile) {
      return;
    }

    setSubmitting(true);
    setError(null);

    try {
      await updateProfile(profile.id, {
        fullName: profile.fullName,
        headline: profile.headline,
        email: profile.email,
        about: draft,
        phone: profile.phone ?? '',
        location: profile.location ?? '',
        githubUrl: profile.githubUrl ?? '',
        profileImageUrl: profile.profileImageUrl ?? '',
        linkedinUrl: profile.linkedinUrl ?? '',
        resumeUrl: profile.resumeUrl ?? '',
        codeforcesUrl: profile.codeforcesUrl ?? '',
        leetcodeUrl: profile.leetcodeUrl ?? '',
      });

      setIsEditing(false);
      await refetch();
    } catch (err) {
      if (axios.isAxiosError<ApiErrorResponse>(err) && err.response?.data) {
        setError(err.response.data.message);
      } else {
        setError('Something went wrong. Please try again.');
      }
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <section id="about" className="section section-plaque">
      <div className="section-heading-row">
        <h2 className="about-heading">
          About <span className="accent">Me</span>
          <span className="about-underline" />
        </h2>
        {isAuthenticated && !isEditing && (
          <button type="button" className="edit-icon-button" onClick={startEdit}>
            &#9998; Edit
          </button>
        )}
      </div>

      {isEditing ? (
        <form className="inline-edit-form" onSubmit={handleSubmit}>
          {error && <p className="form-error">{error}</p>}
          <label>
            About
            <textarea value={draft} onChange={(e) => setDraft(e.target.value)} />
          </label>
          <div className="inline-form-actions">
            <button type="submit" disabled={submitting}>
              Save
            </button>
            <button type="button" onClick={() => setIsEditing(false)}>
              Cancel
            </button>
          </div>
        </form>
      ) : (
        <p>{profile?.about}</p>
      )}
    </section>
  );
}
