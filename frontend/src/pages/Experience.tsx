import { useState } from 'react';
import { useAuth } from '../hooks/useAuth';
import { useInlineCrud } from '../hooks/useInlineCrud';
import { usePortfolio } from '../hooks/usePortfolio';
import {
  createExperience,
  updateExperience,
  deleteExperience,
} from '../services/experienceAdminService';
import { getTechList } from '../utils/project';
import nomuraIcon from '../assets/nomura-icon.jpeg';
import drdoIcon from '../assets/drdo.png';
import type { Experience as ExperienceItem } from '../types/portfolio';
import type { ExperienceRequest } from '../types/requests';

const emptyForm: ExperienceRequest = {
  company: '',
  jobTitle: '',
  employmentType: '',
  location: '',
  startDate: '',
  endDate: '',
  currentlyWorking: false,
  description: '',
  technologies: '',
  featured: false,
};

function formatMonthYear(dateStr: string | null): string {
  if (!dateStr) return '';
  const date = new Date(dateStr);
  if (Number.isNaN(date.getTime())) return dateStr;
  return date.toLocaleDateString('en-US', { month: 'short', year: 'numeric' });
}

function getCategoryIcon(type: string | null | undefined): string {
  const normalized = (type ?? '').toLowerCase();
  if (normalized.includes('intern')) return '💼';
  if (normalized.includes('research')) return '📄';
  if (normalized.includes('project')) return '🚀';
  if (normalized.includes('full')) return '💻';
  return '💼';
}

function getCompanyLogo(company: string): string | null {
  const normalized = company.toLowerCase();
  if (normalized.includes('nomura')) return nomuraIcon;
  if (normalized.includes('drdo')) return drdoIcon;
  return null;
}

export function Experience() {
  const { data } = usePortfolio();
  const { isAuthenticated } = useAuth();
  const [activeFilter, setActiveFilter] = useState<string | null>(null);
  const [activeIndex, setActiveIndex] = useState(0);

  const sorted = [...(data?.experience ?? [])].sort(
    (a, b) => new Date(b.startDate).getTime() - new Date(a.startDate).getTime(),
  );

  const categories = Array.from(
    new Set(
      sorted
        .map((exp) => exp.employmentType?.trim())
        .filter((type): type is string => Boolean(type)),
    ),
  );

  const filtered = activeFilter
    ? sorted.filter((exp) => exp.employmentType?.trim() === activeFilter)
    : sorted;

  const crud = useInlineCrud<ExperienceItem, ExperienceRequest>({
    service: { create: createExperience, update: updateExperience, remove: deleteExperience },
    emptyForm,
    toFormValues: (exp) => ({
      company: exp.company,
      jobTitle: exp.jobTitle,
      employmentType: exp.employmentType ?? '',
      location: exp.location ?? '',
      startDate: exp.startDate,
      endDate: exp.endDate ?? '',
      currentlyWorking: exp.currentlyWorking,
      description: exp.description ?? '',
      technologies: exp.technologies ?? '',
      featured: exp.featured,
    }),
    getId: (exp) => exp.id,
    getDisplayName: (exp) => `${exp.jobTitle} at ${exp.company}`,
  });

  const safeIndex = Math.min(activeIndex, Math.max(filtered.length - 1, 0));
  const activeExp = filtered[safeIndex];
  const isEditingActive = activeExp !== undefined && crud.editingId === activeExp.id;

  const selectFilter = (filter: string | null) => {
    setActiveFilter(filter);
    setActiveIndex(0);
  };

  const goPrev = () => setActiveIndex((safeIndex - 1 + filtered.length) % filtered.length);
  const goNext = () => setActiveIndex((safeIndex + 1) % filtered.length);

  const renderForm = (key: string) => (
    <form className="inline-edit-form" onSubmit={crud.handleSubmit} key={key}>
      {crud.fieldErrors._general && <p className="form-error">{crud.fieldErrors._general}</p>}

      <label>
        Company
        <input
          value={crud.form.company}
          onChange={(e) => crud.handleChange('company', e.target.value)}
          required
        />
      </label>

      <label>
        Job Title
        <input
          value={crud.form.jobTitle}
          onChange={(e) => crud.handleChange('jobTitle', e.target.value)}
          required
        />
      </label>

      <label>
        Employment Type
        <input
          value={crud.form.employmentType}
          onChange={(e) => crud.handleChange('employmentType', e.target.value)}
          placeholder="Internship, Full-time, Research..."
        />
      </label>

      <label>
        Location
        <input
          value={crud.form.location}
          onChange={(e) => crud.handleChange('location', e.target.value)}
        />
      </label>

      <label>
        Start Date
        <input
          type="date"
          value={crud.form.startDate}
          onChange={(e) => crud.handleChange('startDate', e.target.value)}
          required
        />
      </label>

      <label>
        End Date
        <input
          type="date"
          value={crud.form.endDate}
          onChange={(e) => crud.handleChange('endDate', e.target.value)}
          disabled={crud.form.currentlyWorking}
        />
      </label>

      <label>
        <input
          type="checkbox"
          checked={crud.form.currentlyWorking}
          onChange={(e) => crud.handleChange('currentlyWorking', e.target.checked)}
        />
        {' '}Currently Working Here
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
          placeholder="Python, LangChain, RAG, LLM"
        />
      </label>

      <label>
        <input
          type="checkbox"
          checked={crud.form.featured}
          onChange={(e) => crud.handleChange('featured', e.target.checked)}
        />
        {' '}Featured
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

  if (sorted.length === 0 && !isAuthenticated) {
    return null;
  }

  return (
    <div className="experience-page" id="experience">
      <div className="experience-header">
        <div className="experience-header-tag">
          {/* <span>01</span> */}
          <span className="experience-header-line" />
        </div>
        <h1>Experience</h1>
        <p className="experience-tagline">A journey of learning, building and creating impact.</p>
      </div>

      <div className="experience-layout">
        <aside className="experience-sidebar">
          <button
            type="button"
            className={
              activeFilter === null ? 'experience-filter-item active' : 'experience-filter-item'
            }
            onClick={() => selectFilter(null)}
          >
            <span>🗂 All Experience</span>
            <span className="experience-filter-count">{sorted.length}</span>
          </button>

          {categories.map((category) => (
            <button
              key={category}
              type="button"
              className={
                activeFilter === category
                  ? 'experience-filter-item active'
                  : 'experience-filter-item'
              }
              onClick={() => selectFilter(category)}
            >
              <span>
                {getCategoryIcon(category)} {category}
              </span>
              <span className="experience-filter-count">
                {sorted.filter((exp) => exp.employmentType?.trim() === category).length}
              </span>
            </button>
          ))}

          {isAuthenticated && !crud.isAdding && (
            <button type="button" className="experience-add-button" onClick={crud.startAdd}>
              + Add Experience
            </button>
          )}
        </aside>

        <div className="experience-main">
          {crud.isAdding && renderForm('add-new')}

          {!crud.isAdding && filtered.length === 0 && (
            <p className="status-message">No experience in this category yet.</p>
          )}

          {!crud.isAdding &&
            activeExp &&
            (isEditingActive ? (
              renderForm(`edit-${activeExp.id}`)
            ) : (
              <div className="experience-carousel-dark">
                {filtered.length > 1 && (
                  <button
                    type="button"
                    className="carousel-arrow carousel-arrow-dark"
                    onClick={goPrev}
                    aria-label="Previous experience"
                  >
                    &#8249;
                  </button>
                )}

                <div className="experience-card-dark">
                  <div className="experience-card-icon">
                    {getCategoryIcon(activeExp.employmentType)}
                  </div>

                  <div className="experience-card-top-actions">
                    {activeExp.featured && (
                      <span className="featured-badge">&#9733; Featured</span>
                    )}
                    {isAuthenticated && (
                      <div className="experience-edit-actions-dark">
                        <button
                          type="button"
                          onClick={() => crud.startEdit(activeExp)}
                          aria-label="Edit experience"
                        >
                          &#9998;
                        </button>
                        <button
                          type="button"
                          onClick={() => crud.handleDelete(activeExp)}
                          aria-label="Delete experience"
                        >
                          &times;
                        </button>
                      </div>
                    )}
                  </div>

                  <div className="experience-avatar">
                    {(() => {
                      const logo = getCompanyLogo(activeExp.company);
                      return logo ? (
                        <img src={logo} alt={activeExp.company} className="experience-avatar-img" />
                      ) : (
                        activeExp.company.charAt(0).toUpperCase()
                      );
                    })()}
                  </div>

                  <h3>
                    {activeExp.jobTitle} &middot; {activeExp.company}
                  </h3>

                  <p className="experience-meta-dark">
                    &#128197; {activeExp.startDate} -{' '}
                    {activeExp.currentlyWorking ? 'Present' : activeExp.endDate}
                    {activeExp.location && <> &middot; &#128205; {activeExp.location}</>}
                    {activeExp.employmentType && (
                      <> &middot; &#128188; {activeExp.employmentType}</>
                    )}
                  </p>

                  {activeExp.description && (
                    <p className="experience-description-dark">{activeExp.description}</p>
                  )}

                  {getTechList(activeExp.technologies).length > 0 && (
                    <ul className="tech-badge-list tech-badge-list-dark">
                      {getTechList(activeExp.technologies).map((tech) => (
                        <li key={tech}>{tech}</li>
                      ))}
                    </ul>
                  )}
                </div>

                {filtered.length > 1 && (
                  <button
                    type="button"
                    className="carousel-arrow carousel-arrow-dark"
                    onClick={goNext}
                    aria-label="Next experience"
                  >
                    &#8250;
                  </button>
                )}
              </div>
            ))}

          {!crud.isAdding && !isEditingActive && filtered.length > 1 && (
            <div className="carousel-dots">
              {filtered.map((exp, idx) => (
                <button
                  key={exp.id}
                  type="button"
                  className={
                    idx === safeIndex ? 'carousel-dot carousel-dot-dark active' : 'carousel-dot carousel-dot-dark'
                  }
                  onClick={() => setActiveIndex(idx)}
                  aria-label={`Go to experience ${idx + 1}`}
                />
              ))}
            </div>
          )}

          {!crud.isAdding && filtered.length > 1 && (
            <div className="experience-timeline">
              <div className="experience-timeline-line" />
              {filtered.map((exp, idx) => (
                <button
                  key={exp.id}
                  type="button"
                  className={
                    idx === safeIndex
                      ? 'experience-timeline-node active'
                      : 'experience-timeline-node'
                  }
                  onClick={() => setActiveIndex(idx)}
                >
                  <span className="experience-timeline-icon">
                    {getCategoryIcon(exp.employmentType)}
                  </span>
                  <span className="experience-timeline-date">{formatMonthYear(exp.startDate)}</span>
                  <span className="experience-timeline-title">{exp.jobTitle}</span>
                  <span className="experience-timeline-company">{exp.company}</span>
                </button>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
