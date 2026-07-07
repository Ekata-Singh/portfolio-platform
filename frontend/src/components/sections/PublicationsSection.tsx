import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { useInlineCrud } from '../../hooks/useInlineCrud';
import { usePortfolio } from '../../hooks/usePortfolio';
import {
  createPublication,
  updatePublication,
  deletePublication,
  uploadPublicationThumbnail,
} from '../../services/publicationAdminService';
import { getTechList } from '../../utils/project';
import { resolveAssetUrl } from '../../utils/resolveAssetUrl';
import { ImageUploadField } from '../ImageUploadField';
import type { Publication } from '../../types/portfolio';
import type { PublicationRequest } from '../../types/requests';

interface PublicationsSectionProps {
  publications: Publication[];
}

const emptyForm: PublicationRequest = {
  title: '',
  publisher: '',
  publicationDate: '',
  publicationUrl: '',
  description: '',
  tags: '',
  thumbnailUrl: '',
  displayOrder: 0,
};

const VISIBLE_TAG_COUNT = 5;

function formatPublishedDate(dateStr: string): string {
  const date = new Date(dateStr);
  if (Number.isNaN(date.getTime())) return dateStr;
  return date.toLocaleDateString('en-US', { month: 'short', day: '2-digit', year: 'numeric' });
}

export function PublicationsSection({ publications }: PublicationsSectionProps) {
  const { isAuthenticated } = useAuth();
  const { refetch } = usePortfolio();
  const [thumbUploading, setThumbUploading] = useState(false);
  const [thumbError, setThumbError] = useState<string | null>(null);
  const sorted = [...publications].sort((a, b) => a.displayOrder - b.displayOrder);

  const crud = useInlineCrud<Publication, PublicationRequest>({
    service: { create: createPublication, update: updatePublication, remove: deletePublication },
    emptyForm,
    toFormValues: (p) => ({
      title: p.title,
      publisher: p.publisher,
      publicationDate: p.publicationDate,
      publicationUrl: p.publicationUrl ?? '',
      description: p.description ?? '',
      tags: p.tags ?? '',
      thumbnailUrl: p.thumbnailUrl ?? '',
      displayOrder: p.displayOrder,
    }),
    getId: (p) => p.id,
    getDisplayName: (p) => p.title,
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
      const updated = await uploadPublicationThumbnail(crud.editingId, file);
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
          Title
          <input
            value={crud.form.title}
            onChange={(e) => crud.handleChange('title', e.target.value)}
            required
          />
          {crud.fieldErrors.title && <span className="form-error">{crud.fieldErrors.title}</span>}
        </label>

        <label>
          Publisher
          <input
            value={crud.form.publisher}
            onChange={(e) => crud.handleChange('publisher', e.target.value)}
            required
            placeholder="IEEE, Springer, ACM..."
          />
          {crud.fieldErrors.publisher && (
            <span className="form-error">{crud.fieldErrors.publisher}</span>
          )}
        </label>

        <label>
          Publication Date
          <input
            type="date"
            value={crud.form.publicationDate}
            onChange={(e) => crud.handleChange('publicationDate', e.target.value)}
            required
          />
          {crud.fieldErrors.publicationDate && (
            <span className="form-error">{crud.fieldErrors.publicationDate}</span>
          )}
        </label>

        <label>
          Tags
          <input
            value={crud.form.tags}
            onChange={(e) => crud.handleChange('tags', e.target.value)}
            placeholder="Antennas, 5G, Wireless Communication"
          />
        </label>

        <label>
          URL
          <input
            value={crud.form.publicationUrl}
            onChange={(e) => crud.handleChange('publicationUrl', e.target.value)}
            placeholder="https://..."
          />
          {crud.fieldErrors.publicationUrl && (
            <span className="form-error">{crud.fieldErrors.publicationUrl}</span>
          )}
        </label>

        <label>
          Description
          <textarea
            value={crud.form.description}
            onChange={(e) => crud.handleChange('description', e.target.value)}
          />
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
          disabledMessage="Save the publication first, then come back to upload a thumbnail."
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
    <div className="publications-page" id="publications">
      <div className="publications-header">
        <div>
          <h2 className="publications-title">
            <span className="heading-dash" /> Publications
          </h2>
          <p className="publications-tagline">Research papers and technical articles I've published.</p>
        </div>

        {isAuthenticated && !crud.isAdding && (
          <button type="button" className="publications-add-button" onClick={crud.startAdd}>
            + Add Publication
          </button>
        )}
      </div>

      <div className="publications-list">
        {isAuthenticated && crud.isAdding && renderForm('add-new')}

        {sorted.map((publication) => {
          if (crud.editingId === publication.id) {
            return renderForm(`edit-${publication.id}`);
          }

          const tags = getTechList(publication.tags);
          const visibleTags = tags.slice(0, VISIBLE_TAG_COUNT);
          const remainingTags = tags.length - visibleTags.length;
          const thumbnailUrl = resolveAssetUrl(publication.thumbnailUrl);

          return (
            <div className="publication-card-dark" key={publication.id}>
              {thumbnailUrl && (
                <div className="publication-thumb-wrap">
                  <img
                    src={thumbnailUrl}
                    alt={publication.title}
                    className="publication-thumb-image"
                    loading="lazy"
                  />
                  <span className="publication-thumb-badge">{publication.publisher}</span>
                </div>
              )}

              <div className="publication-body">
                <div className="publication-top-row">
                  <span className="publication-publisher-pill">{publication.publisher}</span>
                  <span className="publication-date">
                    &#128197; Published on {formatPublishedDate(publication.publicationDate)}
                  </span>

                  {isAuthenticated && (
                    <div className="experience-edit-actions-dark publication-actions">
                      <button
                        type="button"
                        onClick={() => crud.startEdit(publication)}
                        aria-label="Edit publication"
                      >
                        &#9998;
                      </button>
                      <button
                        type="button"
                        onClick={() => crud.handleDelete(publication)}
                        aria-label="Delete publication"
                      >
                        &times;
                      </button>
                    </div>
                  )}
                </div>

                <h3>{publication.title}</h3>
                {publication.description && (
                  <p className="experience-description-dark">{publication.description}</p>
                )}

                {tags.length > 0 && (
                  <ul className="tech-badge-list tech-badge-list-dark">
                    {visibleTags.map((tag) => (
                      <li key={tag}>{tag}</li>
                    ))}
                    {remainingTags > 0 && <li>+{remainingTags}</li>}
                  </ul>
                )}

                {publication.publicationUrl && (
                  <a
                    href={publication.publicationUrl}
                    target="_blank"
                    rel="noreferrer"
                    className="publication-view-btn"
                  >
                    View Paper &#8599;
                  </a>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
