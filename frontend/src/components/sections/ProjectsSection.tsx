import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { useInlineCrud } from '../../hooks/useInlineCrud';
import { usePortfolio } from '../../hooks/usePortfolio';
import {
  createProject,
  updateProject,
  deleteProject,
  uploadProjectThumbnail,
} from '../../services/projectAdminService';
import { getProjectSummary, getTechList } from '../../utils/project';
import { resolveAssetUrl } from '../../utils/resolveAssetUrl';
import { ImageUploadField } from '../ImageUploadField';
import githubIcon from '../../assets/github-icon.jpg';
import type { Project } from '../../types/portfolio';
import type { ProjectRequest } from '../../types/requests';

interface ProjectsSectionProps {
  projects: Project[];
}

const emptyForm: ProjectRequest = {
  projectName: '',
  description: '',
  technologies: '',
  thumbnailUrl: '',
  projectUrl: '',
  githubUrl: '',
  displayOrder: 0,
};

const ITEMS_PER_PAGE = 1;
const VISIBLE_TECH_COUNT = 6;

export function ProjectsSection({ projects }: ProjectsSectionProps) {
  const { isAuthenticated } = useAuth();
  const { data, refetch } = usePortfolio();
  const githubUrl = data?.profile?.githubUrl;
  const [page, setPage] = useState(0);
  const [thumbnailUploading, setThumbnailUploading] = useState(false);
  const [thumbnailError, setThumbnailError] = useState<string | null>(null);
  const sorted = [...projects].sort((a, b) => a.displayOrder - b.displayOrder);

  const crud = useInlineCrud<Project, ProjectRequest>({
    service: { create: createProject, update: updateProject, remove: deleteProject },
    emptyForm,
    toFormValues: (p) => ({
      projectName: p.projectName,
      description: p.description ?? '',
      technologies: p.technologies ?? '',
      thumbnailUrl: p.thumbnailUrl ?? '',
      projectUrl: p.projectUrl ?? '',
      githubUrl: p.githubUrl ?? '',
      displayOrder: p.displayOrder,
    }),
    getId: (p) => p.id,
    getDisplayName: (p) => p.projectName,
  });

  if (sorted.length === 0 && !isAuthenticated) {
    return null;
  }

  const totalPages = Math.max(Math.ceil(sorted.length / ITEMS_PER_PAGE), 1);
  const safePage = Math.min(page, totalPages - 1);
  const pageItems = sorted.slice(safePage * ITEMS_PER_PAGE, safePage * ITEMS_PER_PAGE + ITEMS_PER_PAGE);

  const goPrev = () => setPage((safePage - 1 + totalPages) % totalPages);
  const goNext = () => setPage((safePage + 1) % totalPages);

  const handleThumbnailSelected = async (file: File) => {
    if (!crud.editingId) {
      return;
    }

    setThumbnailUploading(true);
    setThumbnailError(null);

    try {
      const updated = await uploadProjectThumbnail(crud.editingId, file);
      crud.handleChange('thumbnailUrl', updated.thumbnailUrl ?? '');
      await refetch();
    } catch {
      setThumbnailError('Failed to upload thumbnail.');
    } finally {
      setThumbnailUploading(false);
    }
  };

  const renderForm = (key: string) => (
    <form className="inline-edit-form" onSubmit={crud.handleSubmit} key={key}>
      {crud.fieldErrors._general && <p className="form-error">{crud.fieldErrors._general}</p>}

      <label>
        Project Name
        <input
          value={crud.form.projectName}
          onChange={(e) => crud.handleChange('projectName', e.target.value)}
          required
        />
        {crud.fieldErrors.projectName && (
          <span className="form-error">{crud.fieldErrors.projectName}</span>
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
        Technologies
        <input
          value={crud.form.technologies}
          onChange={(e) => crud.handleChange('technologies', e.target.value)}
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
        uploading={thumbnailUploading}
        disabled={!crud.editingId}
        disabledMessage="Save the project first, then come back to upload a thumbnail."
        onFileSelected={handleThumbnailSelected}
      />
      {thumbnailError && <span className="form-error">{thumbnailError}</span>}

      <label>
        Project URL
        <input
          value={crud.form.projectUrl}
          onChange={(e) => crud.handleChange('projectUrl', e.target.value)}
          placeholder="https://..."
        />
        {crud.fieldErrors.projectUrl && (
          <span className="form-error">{crud.fieldErrors.projectUrl}</span>
        )}
      </label>

      <label>
        GitHub URL
        <input
          value={crud.form.githubUrl}
          onChange={(e) => crud.handleChange('githubUrl', e.target.value)}
          placeholder="https://..."
        />
        {crud.fieldErrors.githubUrl && (
          <span className="form-error">{crud.fieldErrors.githubUrl}</span>
        )}
      </label>

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
  );

  const renderCard = (project: Project) => {
    const techList = getTechList(project.technologies);
    const visibleTech = techList.slice(0, VISIBLE_TECH_COUNT);
    const remainingTech = techList.length - visibleTech.length;
    const thumbnailUrl = resolveAssetUrl(project.thumbnailUrl);

    if (crud.editingId === project.id) {
      return renderForm(`edit-${project.id}`);
    }

    return (
      <div className="project-card-horizontal" key={project.id}>
        {thumbnailUrl && (
          <img
            src={thumbnailUrl}
            alt={project.projectName}
            className="project-card-image"
            loading="lazy"
          />
        )}

        <div className="project-card-body">
          <div className="project-card-header">
            <h3>{project.projectName}</h3>
            <div className="project-card-icons">
              {project.projectUrl && (
                <a
                  href={project.projectUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="project-icon-btn"
                  aria-label="Live Demo"
                >
                  &#8599;
                </a>
              )}
              {project.githubUrl && (
                <a
                  href={project.githubUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="project-icon-btn"
                  aria-label="GitHub"
                >
                  <img src={githubIcon} alt="" />
                </a>
              )}
              {isAuthenticated && (
                <>
                  <button
                    type="button"
                    className="project-icon-btn"
                    onClick={() => crud.startEdit(project)}
                    aria-label="Edit project"
                  >
                    &#9998;
                  </button>
                  <button
                    type="button"
                    className="project-icon-btn"
                    onClick={() => crud.handleDelete(project)}
                    aria-label="Delete project"
                  >
                    &times;
                  </button>
                </>
              )}
            </div>
          </div>

          <p>{getProjectSummary(project.description)}</p>

          {visibleTech.length > 0 && (
            <ul className="tech-badge-list">
              {visibleTech.map((tech) => (
                <li key={tech}>{tech}</li>
              ))}
              {remainingTech > 0 && <li>+{remainingTech}</li>}
            </ul>
          )}

          <Link to={`/projects/${project.id}`} className="card-cta-link">
            View Details &rarr;
          </Link>
        </div>
      </div>
    );
  };

  return (
    <section id="projects" className="section">
      <h2 className="projects-heading">
        <span className="heading-dash" />
        Latest <span className="accent">Projects</span>
        <span className="heading-spark">&#10022;</span>
      </h2>

      <div className="section-body expanded">
        {isAuthenticated && crud.isAdding ? (
          renderForm('add-new')
        ) : (
          <div className="projects-carousel">
            {totalPages > 1 && (
              <button
                type="button"
                className="carousel-arrow projects-carousel-arrow"
                onClick={goPrev}
                aria-label="Previous projects"
              >
                &#8249;
              </button>
            )}

            <div className="projects-carousel-track">{pageItems.map(renderCard)}</div>

            {totalPages > 1 && (
              <button
                type="button"
                className="carousel-arrow projects-carousel-arrow"
                onClick={goNext}
                aria-label="Next projects"
              >
                &#8250;
              </button>
            )}
          </div>
        )}

        {totalPages > 1 && !crud.isAdding && (
          <div className="carousel-dots">
            {Array.from({ length: totalPages }, (_, idx) => (
              <button
                key={idx}
                type="button"
                className={idx === safePage ? 'carousel-dot active' : 'carousel-dot'}
                onClick={() => setPage(idx)}
                aria-label={`Go to page ${idx + 1}`}
              />
            ))}
          </div>
        )}

        <div className="projects-cta">
          <img src={githubIcon} alt="" className="projects-cta-icon" />
          {githubUrl ? (
            <a
              href={githubUrl}
              target="_blank"
              rel="noreferrer"
              className="hero-resume-button"
            >
              Explore More Projects
            </a>
          ) : (
            <Link to="/projects" className="hero-resume-button">
              Explore More Projects
            </Link>
          )}
        </div>

        {isAuthenticated && !crud.isAdding && (
          <button type="button" className="add-card-button" onClick={crud.startAdd}>
            + Add Project
          </button>
        )}
      </div>
    </section>
  );
}
