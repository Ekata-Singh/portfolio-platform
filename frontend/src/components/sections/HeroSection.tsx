import { useState, type FormEvent } from 'react';
import axios from 'axios';
import { useAuth } from '../../hooks/useAuth';
import { usePortfolio } from '../../hooks/usePortfolio';
import {
  createProfile,
  updateProfile,
  uploadProfileImage,
  uploadResume,
} from '../../services/profileAdminService';
import { ImageUploadField } from '../ImageUploadField';
import { FileUploadField } from '../FileUploadField';
import { resolveAssetUrl } from '../../utils/resolveAssetUrl';
import type { Profile } from '../../types/portfolio';
import type { ProfileRequest } from '../../types/requests';
import type { ApiErrorResponse } from '../../types/api';
import githubIcon from '../../assets/github-icon.jpg';
import linkedinIcon from '../../assets/linkedin-icon.png';
import codeforcesIcon from '../../assets/codeforces-icon.webp';
import leetcodeIcon from '../../assets/leetcode-icon.webp';

interface HeroSectionProps {
  profile: Profile | null | undefined;
}

const toFormValues = (profile: Profile | null | undefined): ProfileRequest => ({
  fullName: profile?.fullName ?? '',
  headline: profile?.headline ?? '',
  email: profile?.email ?? '',
  about: profile?.about ?? '',
  phone: profile?.phone ?? '',
  location: profile?.location ?? '',
  githubUrl: profile?.githubUrl ?? '',
  profileImageUrl: profile?.profileImageUrl ?? '',
  linkedinUrl: profile?.linkedinUrl ?? '',
  resumeUrl: profile?.resumeUrl ?? '',
  codeforcesUrl: profile?.codeforcesUrl ?? '',
  leetcodeUrl: profile?.leetcodeUrl ?? '',
});

export function HeroSection({ profile }: HeroSectionProps) {
  const { isAuthenticated } = useAuth();
  const { refetch } = usePortfolio();

  const [isEditing, setIsEditing] = useState(false);
  const [form, setForm] = useState<ProfileRequest>(() => toFormValues(profile));
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});
  const [submitting, setSubmitting] = useState(false);
  const [imageUploading, setImageUploading] = useState(false);
  const [resumeUploading, setResumeUploading] = useState(false);

  const avatarUrl = resolveAssetUrl(profile?.profileImageUrl);
  const resumeUrl = resolveAssetUrl(profile?.resumeUrl);
  const [firstName, ...restName] = (profile?.fullName ?? '').split(' ');
  const lastName = restName.join(' ');

  const startEdit = () => {
    setForm(toFormValues(profile));
    setFieldErrors({});
    setIsEditing(true);
  };

  const handleChange = (name: keyof ProfileRequest, value: string) => {
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleImageSelected = async (file: File) => {
    if (!profile) {
      return;
    }

    setImageUploading(true);

    try {
      const updated = await uploadProfileImage(profile.id, file);
      setForm((prev) => ({ ...prev, profileImageUrl: updated.profileImageUrl ?? '' }));
    } catch {
      setFieldErrors((prev) => ({ ...prev, profileImageUrl: 'Failed to upload image.' }));
    } finally {
      setImageUploading(false);
    }
  };

  const handleResumeSelected = async (file: File) => {
    if (!profile) {
      return;
    }

    setResumeUploading(true);

    try {
      const updated = await uploadResume(profile.id, file);
      setForm((prev) => ({ ...prev, resumeUrl: updated.resumeUrl ?? '' }));
    } catch {
      setFieldErrors((prev) => ({ ...prev, resumeUrl: 'Failed to upload resume.' }));
    } finally {
      setResumeUploading(false);
    }
  };

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setSubmitting(true);
    setFieldErrors({});

    try {
      if (profile) {
        await updateProfile(profile.id, form);
      } else {
        await createProfile(form);
      }

      setIsEditing(false);
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

  if (!profile && !isAuthenticated) {
    return null;
  }

  return (
    <section id="home" className="hero">
      {isEditing ? (
        <form className="inline-edit-form" onSubmit={handleSubmit}>
          {fieldErrors._general && <p className="form-error">{fieldErrors._general}</p>}

          <ImageUploadField
            label="Profile Image"
            previewUrl={resolveAssetUrl(form.profileImageUrl)}
            uploading={imageUploading}
            disabled={!profile}
            disabledMessage="Save your profile first, then come back to add a photo."
            onFileSelected={handleImageSelected}
          />
          {fieldErrors.profileImageUrl && (
            <span className="form-error">{fieldErrors.profileImageUrl}</span>
          )}

          <label>
            Full Name
            <input
              value={form.fullName}
              onChange={(e) => handleChange('fullName', e.target.value)}
              required
            />
            {fieldErrors.fullName && (
              <span className="form-error">{fieldErrors.fullName}</span>
            )}
          </label>

          <label>
            Headline
            <input
              value={form.headline}
              onChange={(e) => handleChange('headline', e.target.value)}
              required
            />
            {fieldErrors.headline && (
              <span className="form-error">{fieldErrors.headline}</span>
            )}
          </label>

          <label>
            Email
            <input
              type="email"
              value={form.email}
              onChange={(e) => handleChange('email', e.target.value)}
              required
            />
            {fieldErrors.email && <span className="form-error">{fieldErrors.email}</span>}
          </label>

          <label>
            GitHub URL
            <input
              value={form.githubUrl}
              onChange={(e) => handleChange('githubUrl', e.target.value)}
              placeholder="https://..."
            />
            {fieldErrors.githubUrl && (
              <span className="form-error">{fieldErrors.githubUrl}</span>
            )}
          </label>

          <label>
            LinkedIn URL
            <input
              value={form.linkedinUrl}
              onChange={(e) => handleChange('linkedinUrl', e.target.value)}
              placeholder="https://..."
            />
            {fieldErrors.linkedinUrl && (
              <span className="form-error">{fieldErrors.linkedinUrl}</span>
            )}
          </label>

          <label>
            Codeforces URL
            <input
              value={form.codeforcesUrl}
              onChange={(e) => handleChange('codeforcesUrl', e.target.value)}
              placeholder="https://codeforces.com/profile/..."
            />
            {fieldErrors.codeforcesUrl && (
              <span className="form-error">{fieldErrors.codeforcesUrl}</span>
            )}
          </label>

          <label>
            LeetCode URL
            <input
              value={form.leetcodeUrl}
              onChange={(e) => handleChange('leetcodeUrl', e.target.value)}
              placeholder="https://leetcode.com/..."
            />
            {fieldErrors.leetcodeUrl && (
              <span className="form-error">{fieldErrors.leetcodeUrl}</span>
            )}
          </label>

          <label>
            Resume URL
            <input
              value={form.resumeUrl}
              onChange={(e) => handleChange('resumeUrl', e.target.value)}
              placeholder="https://... (or upload a PDF below)"
            />
            {fieldErrors.resumeUrl && (
              <span className="form-error">{fieldErrors.resumeUrl}</span>
            )}
          </label>

          <FileUploadField
            label="Or Upload Resume (PDF)"
            accept="application/pdf"
            currentUrl={resolveAssetUrl(form.resumeUrl)}
            uploading={resumeUploading}
            disabled={!profile}
            disabledMessage="Save your profile first, then come back to upload a resume."
            onFileSelected={handleResumeSelected}
          />

          <div className="inline-form-actions">
            <button type="submit" disabled={submitting}>
              {profile ? 'Save' : 'Create Profile'}
            </button>
            {profile && (
              <button type="button" onClick={() => setIsEditing(false)}>
                Cancel
              </button>
            )}
          </div>
        </form>
      ) : (
        <>
          {isAuthenticated && (
            <button
              type="button"
              className="edit-icon-button hero-edit-button"
              onClick={startEdit}
            >
              &#9998; Edit
            </button>
          )}

          {avatarUrl && (
            <img src={avatarUrl} alt={profile?.fullName} className="hero-avatar" />
          )}
          <p className="hero-greeting">&#10022; Hello, I'm &#10022;</p>
          <h1>
            {firstName} <span className="accent">{lastName}</span>
          </h1>
          <p className="headline">{profile?.headline}</p>
          <span className="hero-divider" />

          <div className="hero-links">
            {profile?.githubUrl && (
              <a
                href={profile.githubUrl}
                target="_blank"
                rel="noreferrer"
                className="social-icon-link"
                aria-label="GitHub"
              >
                <img src={githubIcon} alt="" />
              </a>
            )}
            {profile?.linkedinUrl && (
              <a
                href={profile.linkedinUrl}
                target="_blank"
                rel="noreferrer"
                className="social-icon-link"
                aria-label="LinkedIn"
              >
                <img src={linkedinIcon} alt="" />
              </a>
            )}
            {profile?.codeforcesUrl && (
              <a
                href={profile.codeforcesUrl}
                target="_blank"
                rel="noreferrer"
                className="social-icon-link"
                aria-label="Codeforces"
              >
                <img src={codeforcesIcon} alt="" />
              </a>
            )}
            {profile?.leetcodeUrl && (
              <a
                href={profile.leetcodeUrl}
                target="_blank"
                rel="noreferrer"
                className="social-icon-link"
                aria-label="LeetCode"
              >
                <img src={leetcodeIcon} alt="" />
              </a>
            )}
          </div>

          {resumeUrl && (
            <a
              href={resumeUrl}
              target="_blank"
              rel="noreferrer"
              className="hero-resume-button"
            >
              &#8595; Download Resume
            </a>
          )}

          {(profile?.location || profile?.email) && (
            <div className="hero-contact-row">
              {profile?.location && (
                <span className="hero-contact-item">&#128205; {profile.location}</span>
              )}
              {profile?.email && (
                <span className="hero-contact-item">&#9993; {profile.email}</span>
              )}
            </div>
          )}
        </>
      )}
    </section>
  );
}
