import { useAuth } from '../../hooks/useAuth';
import { useInlineCrud } from '../../hooks/useInlineCrud';
import {
  createEducation,
  updateEducation,
  deleteEducation,
} from '../../services/educationAdminService';
import { getTechList } from '../../utils/project';
import type { Education } from '../../types/portfolio';
import type { EducationRequest } from '../../types/requests';

interface EducationSectionProps {
  education: Education[];
}

const emptyForm: EducationRequest = {
  institution: '',
  degree: '',
  fieldOfStudy: '',
  startYear: new Date().getFullYear(),
  endYear: new Date().getFullYear(),
  grade: '',
  description: '',
  subjects: '',
};

const VISIBLE_SUBJECT_COUNT = 4;

export function EducationSection({ education }: EducationSectionProps) {
  const { isAuthenticated } = useAuth();

  const sorted = [...education].sort((a, b) => b.startYear - a.startYear);

  const crud = useInlineCrud<Education, EducationRequest>({
    service: { create: createEducation, update: updateEducation, remove: deleteEducation },
    emptyForm,
    toFormValues: (edu) => ({
      institution: edu.institution,
      degree: edu.degree,
      fieldOfStudy: edu.fieldOfStudy,
      startYear: edu.startYear,
      endYear: edu.endYear ?? new Date().getFullYear(),
      grade: edu.grade ?? '',
      description: edu.description ?? '',
      subjects: edu.subjects ?? '',
    }),
    getId: (edu) => edu.id,
    getDisplayName: (edu) => edu.institution,
  });

  if (sorted.length === 0 && !isAuthenticated) {
    return null;
  }

  const renderForm = (key: string) => (
    <div className="education-timeline-row" key={key}>
      <form className="inline-edit-form" onSubmit={crud.handleSubmit}>
        {crud.fieldErrors._general && (
          <p className="form-error">{crud.fieldErrors._general}</p>
        )}

        <label>
          Institution
          <input
            value={crud.form.institution}
            onChange={(e) => crud.handleChange('institution', e.target.value)}
            required
          />
        </label>

        <label>
          Degree
          <input
            value={crud.form.degree}
            onChange={(e) => crud.handleChange('degree', e.target.value)}
            required
          />
        </label>

        <label>
          Field of Study
          <input
            value={crud.form.fieldOfStudy}
            onChange={(e) => crud.handleChange('fieldOfStudy', e.target.value)}
            required
          />
        </label>

        <label>
          Start Year
          <input
            type="number"
            value={crud.form.startYear}
            onChange={(e) => crud.handleChange('startYear', Number(e.target.value))}
            required
          />
        </label>

        <label>
          End Year
          <input
            type="number"
            value={crud.form.endYear}
            onChange={(e) => crud.handleChange('endYear', Number(e.target.value))}
          />
        </label>

        <label>
          Grade
          <input
            value={crud.form.grade}
            onChange={(e) => crud.handleChange('grade', e.target.value)}
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
          Subjects
          <input
            value={crud.form.subjects}
            onChange={(e) => crud.handleChange('subjects', e.target.value)}
            placeholder="Core Subjects, Digital Systems, Signal Processing"
          />
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
    <div className="education-page" id="education">
      <div className="education-header">
        <span className="education-header-icon">&#127891;</span>
        <h2>Education</h2>
        <span className="education-header-line" />
        <span className="education-header-spark">&#10022;</span>
      </div>

      <div className="education-timeline">
        <div className="education-timeline-line" />

        {sorted.map((edu) => {
          if (crud.editingId === edu.id) {
            return renderForm(`edit-${edu.id}`);
          }

          const subjects = getTechList(edu.subjects);
          const visibleSubjects = subjects.slice(0, VISIBLE_SUBJECT_COUNT);
          const remainingCount = subjects.length - visibleSubjects.length;

          return (
            <div className="education-timeline-row" key={edu.id}>
              <div className="education-timeline-node">&#127963;</div>

              <div className="education-card-dark">
                {isAuthenticated && (
                  <div className="experience-edit-actions-dark education-card-actions">
                    <button
                      type="button"
                      onClick={() => crud.startEdit(edu)}
                      aria-label="Edit education"
                    >
                      &#9998;
                    </button>
                    <button
                      type="button"
                      onClick={() => crud.handleDelete(edu)}
                      aria-label="Delete education"
                    >
                      &times;
                    </button>
                  </div>
                )}

                <h3>{edu.degree}</h3>
                <p className="education-institution">{edu.institution}</p>
                <p className="education-field">{edu.fieldOfStudy}</p>
                <p className="education-meta-dark">
                  {edu.startYear} - {edu.endYear ?? 'Present'}
                  {edu.grade && ` · Grade: ${edu.grade}`}
                </p>
                {edu.description && (
                  <p className="experience-description-dark">{edu.description}</p>
                )}

                {subjects.length > 0 && (
                  <ul className="tech-badge-list tech-badge-list-dark">
                    {visibleSubjects.map((subject) => (
                      <li key={subject}>{subject}</li>
                    ))}
                    {remainingCount > 0 && <li>+{remainingCount}</li>}
                  </ul>
                )}
              </div>
            </div>
          );
        })}

        {isAuthenticated && crud.isAdding && renderForm('add-new')}
      </div>

      {isAuthenticated && !crud.isAdding && (
        <button type="button" className="education-add-button" onClick={crud.startAdd}>
          + Add Education
        </button>
      )}
    </div>
  );
}
