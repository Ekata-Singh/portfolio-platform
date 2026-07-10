import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { useInlineCrud } from '../../hooks/useInlineCrud';
import { usePortfolio } from '../../hooks/usePortfolio';
import {
  createAchievement,
  updateAchievement,
  deleteAchievement,
  uploadAchievementCertificate,
} from '../../services/achievementAdminService';
import { resolveAssetUrl } from '../../utils/resolveAssetUrl';
import { FileUploadField } from '../FileUploadField';
import type { Achievement } from '../../types/portfolio';
import type { AchievementRequest } from '../../types/requests';

interface AchievementsSectionProps {
  achievements: Achievement[];
}

const emptyForm: AchievementRequest = {
  title: '',
  organization: '',
  achievementDate: '',
  description: '',
  achievementUrl: '',
  category: '',
  status: '',
  certificateFileUrl: '',
  displayOrder: 0,
};

function isPdfFile(url: string): boolean {
  return url.toLowerCase().endsWith('.pdf');
}

function getCategoryIcon(category: string | null | undefined): string {
  const normalized = (category ?? '').toLowerCase();
  if (normalized.includes('hackathon')) return '\u{1F3C6}';
  if (normalized.includes('machine learning') || normalized.includes('ml')) return '\u{1F4CA}';
  if (normalized.includes('research')) return '\u{1F58B}';
  return '\u{1F3C5}';
}

function getStatusIcon(status: string | null | undefined): string {
  const normalized = (status ?? '').toLowerCase();
  if (normalized.includes('final') || normalized.includes('winner')) return '\u{1F3C6}';
  if (normalized.includes('complet')) return '\u{1F393}';
  if (normalized.includes('publish')) return '\u{1F4C4}';
  return '★';
}

function getYear(dateStr: string | null): string {
  if (!dateStr) return '';
  const year = new Date(dateStr).getFullYear();
  return Number.isNaN(year) ? dateStr : String(year);
}

export function AchievementsSection({ achievements }: AchievementsSectionProps) {
  const { isAuthenticated } = useAuth();
  const { refetch } = usePortfolio();
  const [certUploading, setCertUploading] = useState(false);
  const [certError, setCertError] = useState<string | null>(null);
  const sorted = [...achievements].sort((a, b) => a.displayOrder - b.displayOrder);

  const crud = useInlineCrud<Achievement, AchievementRequest>({
    service: { create: createAchievement, update: updateAchievement, remove: deleteAchievement },
    emptyForm,
    toFormValues: (a) => ({
      title: a.title,
      organization: a.organization ?? '',
      achievementDate: a.achievementDate ?? '',
      description: a.description ?? '',
      achievementUrl: a.achievementUrl ?? '',
      category: a.category ?? '',
      status: a.status ?? '',
      certificateFileUrl: a.certificateFileUrl ?? '',
      displayOrder: a.displayOrder,
    }),
    getId: (a) => a.id,
    getDisplayName: (a) => a.title,
  });

  if (sorted.length === 0 && !isAuthenticated) {
    return null;
  }

  const handleCertificateSelected = async (file: File) => {
    if (!crud.editingId) {
      return;
    }

    setCertUploading(true);
    setCertError(null);

    try {
      const updated = await uploadAchievementCertificate(crud.editingId, file);
      crud.handleChange('certificateFileUrl', updated.certificateFileUrl ?? '');
      await refetch();
    } catch {
      setCertError('Failed to upload certificate file.');
    } finally {
      setCertUploading(false);
    }
  };

  const renderForm = (key: string) => (
    <div key={key}>
      <form className="inline-edit-form" onSubmit={crud.handleSubmit}>
        {crud.fieldErrors._general && (
          <p className="form-error">{crud.fieldErrors._general}</p>
        )}

        <label>
          Title
          <input
            value={crud.form.title}
            onChange={(e) => crud.handleChange('title', e.target.value)}
            required
          />
          {crud.fieldErrors.title && <span className="form-error">{crud.fieldErrors.title}</span>}
        </label>

        <label>
          Organization
          <input
            value={crud.form.organization}
            onChange={(e) => crud.handleChange('organization', e.target.value)}
          />
        </label>

        <label>
          Date
          <input
            type="date"
            value={crud.form.achievementDate}
            onChange={(e) => crud.handleChange('achievementDate', e.target.value)}
          />
        </label>

        <label>
          Category
          <input
            value={crud.form.category}
            onChange={(e) => crud.handleChange('category', e.target.value)}
            placeholder="Hackathon, Machine Learning, Research..."
          />
        </label>

        <label>
          Status
          <input
            value={crud.form.status}
            onChange={(e) => crud.handleChange('status', e.target.value)}
            placeholder="Finalist, Completed, Published..."
          />
        </label>

        <label>
          Description
          <textarea
            value={crud.form.description}
            onChange={(e) => crud.handleChange('description', e.target.value)}
          />
        </label>

        <label>
          Certificate URL
          <input
            value={crud.form.achievementUrl}
            onChange={(e) => crud.handleChange('achievementUrl', e.target.value)}
            placeholder="https://..."
          />
          {crud.fieldErrors.achievementUrl && (
            <span className="form-error">{crud.fieldErrors.achievementUrl}</span>
          )}
        </label>

        <label>
          Certificate File URL
          <input
            value={crud.form.certificateFileUrl}
            onChange={(e) => crud.handleChange('certificateFileUrl', e.target.value)}
            placeholder="https://... (image or PDF)"
          />
          {crud.fieldErrors.certificateFileUrl && (
            <span className="form-error">{crud.fieldErrors.certificateFileUrl}</span>
          )}
        </label>

        <FileUploadField
          label="Or Upload Certificate (Image or PDF)"
          accept="image/*,application/pdf"
          currentUrl={resolveAssetUrl(crud.form.certificateFileUrl)}
          uploading={certUploading}
          disabled={!crud.editingId}
          disabledMessage="Save the achievement first, then come back to upload a certificate."
          onFileSelected={handleCertificateSelected}
        />
        {certError && <span className="form-error">{certError}</span>}

        <label>
          Display Order
          <input
            type="number"
            min={0}
            value={crud.form.displayOrder}
            onChange={(e) => crud.handleChange('displayOrder', Number(e.target.value))}
            required
          />
          {crud.fieldErrors.displayOrder && (
            <span className="form-error">{crud.fieldErrors.displayOrder}</span>
          )}
        </label>

        <div className="inline-form-actions">
          <button type="submit" disabled={crud.submitting}>
            {crud.editingId ? 'Save' : 'Add'}
          </button>
          <button type="button" onClick={crud.cancel}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );

  return (
    <div className="achievements-page" id="achievements">
      <div className="achievements-header">
        <h2 className="achievements-title">
          Achievements
          <span className="education-header-line" />
          <span className="heading-spark">&#10022;</span>
        </h2>
        <p className="achievements-tagline">
          Milestones that reflect my passion for learning and building.
        </p>
      </div>

      <div className="achievements-list">
        {sorted.map((achievement) => {
          if (crud.editingId === achievement.id) {
            return renderForm(`edit-${achievement.id}`);
          }

          const certificateFileUrl = resolveAssetUrl(achievement.certificateFileUrl);

          return (
            <div className="achievement-card-light" key={achievement.id}>
              <div className="achievement-icon-col">
                <div className="achievement-icon-box">{getCategoryIcon(achievement.category)}</div>
                {achievement.status && (
                  <span className="achievement-status-pill">
                    {getStatusIcon(achievement.status)} {achievement.status}
                  </span>
                )}
              </div>

              <div className="achievement-body">
                <h3>{achievement.title}</h3>
                {achievement.organization && (
                  <p className="achievement-org">{achievement.organization}</p>
                )}
                <p className="achievement-meta-light">
                  {achievement.achievementDate && (
                    <span>&#128197; {getYear(achievement.achievementDate)}</span>
                  )}
                  {achievement.category && (
                    <span className="achievement-category-pill">{achievement.category}</span>
                  )}
                </p>
                {achievement.description && <p>{achievement.description}</p>}
              </div>

              <div className="achievement-media-col">
                {isAuthenticated && (
                  <div className="experience-edit-actions-dark achievement-media-actions">
                    <button
                      type="button"
                      onClick={() => crud.startEdit(achievement)}
                      aria-label="Edit achievement"
                    >
                      &#9998;
                    </button>
                    <button
                      type="button"
                      onClick={() => crud.handleDelete(achievement)}
                      aria-label="Delete achievement"
                    >
                      &times;
                    </button>
                  </div>
                )}

                {certificateFileUrl && (
                  isPdfFile(certificateFileUrl) ? (
                    <embed
                      src={certificateFileUrl}
                      type="application/pdf"
                      className="achievement-certificate-pdf"
                    />
                  ) : (
                    <img
                      src={certificateFileUrl}
                      alt={achievement.title}
                      className="achievement-certificate-image"
                      loading="lazy"
                    />
                  )
                )}

                {achievement.achievementUrl && (
                  <a
                    href={achievement.achievementUrl}
                    target="_blank"
                    rel="noreferrer"
                    className="achievement-view-cert-btn"
                  >
                    View Certificate &#8599;
                  </a>
                )}
              </div>
            </div>
          );
        })}

        {isAuthenticated && crud.isAdding && renderForm('add-new')}
      </div>

      {isAuthenticated && !crud.isAdding && (
        <button type="button" className="education-add-button" onClick={crud.startAdd}>
          + Add Achievement
        </button>
      )}
    </div>
  );
}
