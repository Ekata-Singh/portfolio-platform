import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { useInlineCrud } from '../../hooks/useInlineCrud';
import { usePortfolio } from '../../hooks/usePortfolio';
import {
  createCertification,
  updateCertification,
  deleteCertification,
  uploadCertificationThumbnail,
} from '../../services/certificationAdminService';
import { resolveAssetUrl } from '../../utils/resolveAssetUrl';
import { ImageUploadField } from '../ImageUploadField';
import type { Certification } from '../../types/portfolio';
import type { CertificationRequest } from '../../types/requests';

interface CertificatesSectionProps {
  certifications: Certification[];
}

const emptyForm: CertificationRequest = {
  certificationName: '',
  issuingOrganization: '',
  issueDate: '',
  expiryDate: '',
  credentialId: '',
  credentialUrl: '',
  type: '',
  category: '',
  thumbnailUrl: '',
  displayOrder: 0,
};

function getCategoryIcon(category: string | null | undefined): string {
  const normalized = (category ?? '').toLowerCase();
  if (normalized.includes('hackathon')) return '\u{1F3C6}';
  if (normalized.includes('machine learning') || normalized.includes('ml')) return '\u{1F4CA}';
  if (normalized.includes('research')) return '\u{1F58B}';
  return '\u{1F3C5}';
}

function getYear(dateStr: string | null): string {
  if (!dateStr) return '';
  const year = new Date(dateStr).getFullYear();
  return Number.isNaN(year) ? dateStr : String(year);
}

export function CertificatesSection({ certifications }: CertificatesSectionProps) {
  const { isAuthenticated } = useAuth();
  const { refetch } = usePortfolio();
  const [thumbUploading, setThumbUploading] = useState(false);
  const [thumbError, setThumbError] = useState<string | null>(null);
  const [viewingImage, setViewingImage] = useState<string | null>(null);
  const sorted = [...certifications].sort((a, b) => a.displayOrder - b.displayOrder);

  const crud = useInlineCrud<Certification, CertificationRequest>({
    service: {
      create: createCertification,
      update: updateCertification,
      remove: deleteCertification,
    },
    emptyForm,
    toFormValues: (c) => ({
      certificationName: c.certificationName,
      issuingOrganization: c.issuingOrganization,
      issueDate: c.issueDate ?? '',
      expiryDate: c.expiryDate ?? '',
      credentialId: c.credentialId ?? '',
      credentialUrl: c.credentialUrl ?? '',
      type: c.type ?? '',
      category: c.category ?? '',
      thumbnailUrl: c.thumbnailUrl ?? '',
      displayOrder: c.displayOrder,
    }),
    getId: (c) => c.id,
    getDisplayName: (c) => c.certificationName,
  });

  if (sorted.length === 0 && !isAuthenticated) {
    return null;
  }

  const handleThumbnailSelected = async (file: File) => {
    if (!crud.editingId) {
      return;
    }

    setThumbUploading(true);
    setThumbError(null);

    try {
      const updated = await uploadCertificationThumbnail(crud.editingId, file);
      crud.handleChange('thumbnailUrl', updated.thumbnailUrl ?? '');
      await refetch();
    } catch {
      setThumbError('Failed to upload thumbnail.');
    } finally {
      setThumbUploading(false);
    }
  };

  const renderForm = (key: string) => (
    <div key={key}>
      <form className="inline-edit-form" onSubmit={crud.handleSubmit}>
        {crud.fieldErrors._general && (
          <p className="form-error">{crud.fieldErrors._general}</p>
        )}

        <label>
          Certification Name
          <input
            value={crud.form.certificationName}
            onChange={(e) => crud.handleChange('certificationName', e.target.value)}
            required
          />
          {crud.fieldErrors.certificationName && (
            <span className="form-error">{crud.fieldErrors.certificationName}</span>
          )}
        </label>

        <label>
          Issuing Organization
          <input
            value={crud.form.issuingOrganization}
            onChange={(e) => crud.handleChange('issuingOrganization', e.target.value)}
            required
          />
          {crud.fieldErrors.issuingOrganization && (
            <span className="form-error">{crud.fieldErrors.issuingOrganization}</span>
          )}
        </label>

        <label>
          Issue Date
          <input
            type="date"
            value={crud.form.issueDate}
            onChange={(e) => crud.handleChange('issueDate', e.target.value)}
          />
        </label>

        <label>
          Expiry Date
          <input
            type="date"
            value={crud.form.expiryDate}
            onChange={(e) => crud.handleChange('expiryDate', e.target.value)}
          />
        </label>

        <label>
          Type
          <input
            value={crud.form.type}
            onChange={(e) => crud.handleChange('type', e.target.value)}
            placeholder="Achievement, Course, Publication..."
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
          Credential ID
          <input
            value={crud.form.credentialId}
            onChange={(e) => crud.handleChange('credentialId', e.target.value)}
          />
        </label>

        <label>
          Credential URL
          <input
            value={crud.form.credentialUrl}
            onChange={(e) => crud.handleChange('credentialUrl', e.target.value)}
            placeholder="https://..."
          />
          {crud.fieldErrors.credentialUrl && (
            <span className="form-error">{crud.fieldErrors.credentialUrl}</span>
          )}
        </label>

        <label>
          Thumbnail Image URL
          <input
            value={crud.form.thumbnailUrl}
            onChange={(e) => crud.handleChange('thumbnailUrl', e.target.value)}
            placeholder="https://..."
          />
          {crud.fieldErrors.thumbnailUrl && (
            <span className="form-error">{crud.fieldErrors.thumbnailUrl}</span>
          )}
        </label>

        <ImageUploadField
          label="Or Upload Thumbnail Image"
          previewUrl={resolveAssetUrl(crud.form.thumbnailUrl)}
          uploading={thumbUploading}
          disabled={!crud.editingId}
          disabledMessage="Save the certificate first, then come back to upload a thumbnail."
          onFileSelected={handleThumbnailSelected}
        />
        {thumbError && <span className="form-error">{thumbError}</span>}

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
    <div className="certificates-page" id="certificates">
      <div className="certificates-header">
        <div>
          <h2 className="certificates-title">
            <span className="heading-dash" /> Certificates
            <span className="heading-dash" />
            <span className="heading-spark">&#10022;</span>
          </h2>
          <p className="certificates-tagline">
            Professional certifications and courses that enhance my skills and knowledge.
          </p>
        </div>

        {isAuthenticated && !crud.isAdding && (
          <button type="button" className="publications-add-button" onClick={crud.startAdd}>
            + Add Certificate
          </button>
        )}
      </div>

      <div className="certificates-list">
        {isAuthenticated && crud.isAdding && renderForm('add-new')}

        {sorted.map((certification) => {
          if (crud.editingId === certification.id) {
            return renderForm(`edit-${certification.id}`);
          }

          const thumbnailUrl = resolveAssetUrl(certification.thumbnailUrl);

          return (
            <div className="certificate-card-dark" key={certification.id}>
              {thumbnailUrl && (
                <div className="certificate-thumb-wrap">
                  <img
                    src={thumbnailUrl}
                    alt={certification.certificationName}
                    className="certificate-thumb-image"
                    loading="lazy"
                  />
                  <button
                    type="button"
                    className="certificate-zoom-btn"
                    onClick={() => setViewingImage(thumbnailUrl)}
                    aria-label="View full certificate image"
                  >
                    &#128269;
                  </button>
                </div>
              )}

              <div className="certificate-body">
                <div className="certificate-top-row">
                  <div className="certificate-icon-box">
                    {getCategoryIcon(certification.category)}
                  </div>
                  {certification.type && (
                    <span className="certificate-type-badge">{certification.type}</span>
                  )}

                  {isAuthenticated && (
                    <div className="experience-edit-actions-dark certificate-actions">
                      <button
                        type="button"
                        onClick={() => crud.startEdit(certification)}
                        aria-label="Edit certification"
                      >
                        &#9998;
                      </button>
                      <button
                        type="button"
                        onClick={() => crud.handleDelete(certification)}
                        aria-label="Delete certification"
                      >
                        &times;
                      </button>
                    </div>
                  )}
                </div>

                <h3>{certification.certificationName}</h3>
                <p className="certificate-org">{certification.issuingOrganization}</p>

                <p className="publication-date">
                  {certification.issueDate && <>&#128197; {getYear(certification.issueDate)}</>}
                  {certification.category && (
                    <span className="achievement-category-pill">{certification.category}</span>
                  )}
                </p>

                {certification.credentialUrl && (
                  <a
                    href={certification.credentialUrl}
                    target="_blank"
                    rel="noreferrer"
                    className="publication-view-btn"
                  >
                    View Certificate &#8599;
                  </a>
                )}
              </div>
            </div>
          );
        })}
      </div>

      <div className="certificates-footer-note">
        <span className="certificate-icon-box certificates-footer-icon">&#127941;</span>
        <div>
          <p className="certificates-footer-title">Keep learning, keep growing.</p>
          <p className="certificates-footer-subtitle">More achievements coming soon!</p>
        </div>
      </div>

      {viewingImage && (
        <div className="modal-backdrop" onClick={() => setViewingImage(null)}>
          <div
            className="modal-panel certificate-lightbox-panel"
            onClick={(event) => event.stopPropagation()}
            role="dialog"
            aria-modal="true"
          >
            <button
              type="button"
              className="modal-close"
              onClick={() => setViewingImage(null)}
              aria-label="Close image preview"
            >
              &times;
            </button>
            <img src={viewingImage} alt="Certificate preview" className="certificate-lightbox-image" />
          </div>
        </div>
      )}
    </div>
  );
}
