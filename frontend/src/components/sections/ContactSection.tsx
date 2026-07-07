import { useState, type FormEvent } from 'react';
import axios from 'axios';
import { useAuth } from '../../hooks/useAuth';
import { usePortfolio } from '../../hooks/usePortfolio';
import { useSectionExpand } from '../../hooks/useSectionExpand';
import { createContact, updateContact } from '../../services/contactAdminService';
import type { Contact, Profile } from '../../types/portfolio';
import type { ContactRequest } from '../../types/requests';
import type { ApiErrorResponse } from '../../types/api';

interface ContactSectionProps {
  contact: Contact | null | undefined;
  profile: Profile | null | undefined;
}

const emptyForm: ContactRequest = {
  email: '',
  phone: '',
  address: '',
  city: '',
  state: '',
  country: '',
  postalCode: '',
};

export function ContactSection({ contact, profile }: ContactSectionProps) {
  const { isAuthenticated } = useAuth();
  const { refetch } = usePortfolio();
  const [isExpanded, setIsExpanded] = useSectionExpand('contact');

  const [isEditing, setIsEditing] = useState(false);
  const [form, setForm] = useState<ContactRequest>(emptyForm);
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});
  const [submitting, setSubmitting] = useState(false);

  const email = contact?.email ?? profile?.email;

  const location = [
    contact?.address,
    contact?.city,
    contact?.state,
    contact?.country,
    contact?.postalCode,
  ]
    .filter(Boolean)
    .join(', ');

  const startEdit = () => {
    setForm({
      email: contact?.email ?? '',
      phone: contact?.phone ?? '',
      address: contact?.address ?? '',
      city: contact?.city ?? '',
      state: contact?.state ?? '',
      country: contact?.country ?? '',
      postalCode: contact?.postalCode ?? '',
    });
    setFieldErrors({});
    setIsEditing(true);
  };

  const handleChange = (name: keyof ContactRequest, value: string) => {
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setSubmitting(true);
    setFieldErrors({});

    try {
      if (contact) {
        await updateContact(contact.id, form);
      } else {
        await createContact(form);
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

  return (
    <section id="contact" className="section">
      <div className="section-heading-row">
        <div
          className="section-toggle"
          onClick={() => setIsExpanded((prev) => !prev)}
          onKeyDown={(e) => {
            if (e.key === 'Enter' || e.key === ' ') {
              e.preventDefault();
              setIsExpanded((prev) => !prev);
            }
          }}
          role="button"
          tabIndex={0}
          aria-expanded={isExpanded}
        >
          <h2>Contact</h2>
          <span className={isExpanded ? 'section-chevron open' : 'section-chevron'}>
            &#9662;
          </span>
        </div>
        {isAuthenticated && !isEditing && (
          <button
            type="button"
            className="edit-icon-button"
            onClick={(e) => {
              e.stopPropagation();
              startEdit();
              setIsExpanded(true);
            }}
          >
            &#9998; {contact ? 'Edit' : 'Add'}
          </button>
        )}
      </div>

      <div className={isExpanded ? 'section-body expanded' : 'section-body'}>
      {isEditing ? (
        <form className="inline-edit-form" onSubmit={handleSubmit}>
          {fieldErrors._general && <p className="form-error">{fieldErrors._general}</p>}

          <label>
            Email
            <input
              value={form.email}
              onChange={(e) => handleChange('email', e.target.value)}
              required
            />
            {fieldErrors.email && <span className="form-error">{fieldErrors.email}</span>}
          </label>

          <label>
            Phone
            <input
              value={form.phone}
              onChange={(e) => handleChange('phone', e.target.value)}
              required
            />
            {fieldErrors.phone && <span className="form-error">{fieldErrors.phone}</span>}
          </label>

          <label>
            Address
            <input
              value={form.address}
              onChange={(e) => handleChange('address', e.target.value)}
            />
          </label>

          <label>
            City
            <input
              value={form.city}
              onChange={(e) => handleChange('city', e.target.value)}
            />
          </label>

          <label>
            State
            <input
              value={form.state}
              onChange={(e) => handleChange('state', e.target.value)}
            />
          </label>

          <label>
            Country
            <input
              value={form.country}
              onChange={(e) => handleChange('country', e.target.value)}
            />
          </label>

          <label>
            Postal Code
            <input
              value={form.postalCode}
              onChange={(e) => handleChange('postalCode', e.target.value)}
            />
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
        <ul className="contact-details">
          {email && (
            <li>
              Email: <a href={`mailto:${email}`}>{email}</a>
            </li>
          )}
          {contact?.phone && <li>Phone: {contact.phone}</li>}
          {location && <li>Location: {location}</li>}
          {profile?.githubUrl && (
            <li>
              <a href={profile.githubUrl} target="_blank" rel="noreferrer">
                GitHub
              </a>
            </li>
          )}
          {profile?.linkedinUrl && (
            <li>
              <a href={profile.linkedinUrl} target="_blank" rel="noreferrer">
                LinkedIn
              </a>
            </li>
          )}
        </ul>
      )}
      </div>
    </section>
  );
}
