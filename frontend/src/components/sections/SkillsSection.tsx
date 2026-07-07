import { useAuth } from '../../hooks/useAuth';
import { useInlineCrud } from '../../hooks/useInlineCrud';
import { useSectionExpand } from '../../hooks/useSectionExpand';
import { createSkill, updateSkill, deleteSkill } from '../../services/skillAdminService';
import type { Skill } from '../../types/portfolio';
import type { SkillRequest } from '../../types/requests';

interface SkillsSectionProps {
  skills: Skill[];
}

const emptyForm: SkillRequest = {
  skillName: '',
  category: '',
  proficiency: '',
  displayOrder: 0,
};

export function SkillsSection({ skills }: SkillsSectionProps) {
  const { isAuthenticated } = useAuth();
  const [isExpanded, setIsExpanded] = useSectionExpand('skills');
  const sorted = [...skills].sort((a, b) => a.displayOrder - b.displayOrder);

  const crud = useInlineCrud<Skill, SkillRequest>({
    service: { create: createSkill, update: updateSkill, remove: deleteSkill },
    emptyForm,
    toFormValues: (s) => ({
      skillName: s.skillName,
      category: s.category ?? '',
      proficiency: s.proficiency ?? '',
      displayOrder: s.displayOrder,
    }),
    getId: (s) => s.id,
    getDisplayName: (s) => s.skillName,
  });

  if (sorted.length === 0 && !isAuthenticated) {
    return null;
  }

  const renderForm = (key: string) => (
    <form className="inline-edit-form" onSubmit={crud.handleSubmit} key={key}>
      {crud.fieldErrors._general && <p className="form-error">{crud.fieldErrors._general}</p>}

      <label>
        Skill Name
        <input
          value={crud.form.skillName}
          onChange={(e) => crud.handleChange('skillName', e.target.value)}
          required
        />
        {crud.fieldErrors.skillName && (
          <span className="form-error">{crud.fieldErrors.skillName}</span>
        )}
      </label>

      <label>
        Category
        <input
          value={crud.form.category}
          onChange={(e) => crud.handleChange('category', e.target.value)}
        />
      </label>

      <label>
        Proficiency
        <input
          value={crud.form.proficiency}
          onChange={(e) => crud.handleChange('proficiency', e.target.value)}
        />
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

  return (
    <section id="skills" className="section section-tone-b">
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
        <h2>Skills</h2>
        <span className={isExpanded ? 'section-chevron open' : 'section-chevron'}>
          &#9662;
        </span>
      </div>

      <div className={isExpanded ? 'section-body expanded' : 'section-body'}>
        <ul className="tag-list">
          {sorted.map((skill) =>
            crud.editingId === skill.id ? (
              <li key={skill.id}>{renderForm(`edit-${skill.id}`)}</li>
            ) : (
              <li key={skill.id} className={isAuthenticated ? 'tag-editable' : undefined}>
                {skill.skillName}
                {isAuthenticated && (
                  <span className="tag-edit-actions">
                    <button
                      type="button"
                      onClick={() => crud.startEdit(skill)}
                      aria-label="Edit skill"
                    >
                      &#9998;
                    </button>
                    <button
                      type="button"
                      onClick={() => crud.handleDelete(skill)}
                      aria-label="Delete skill"
                    >
                      &times;
                    </button>
                  </span>
                )}
              </li>
            ),
          )}

          {isAuthenticated && crud.isAdding && <li>{renderForm('add-new')}</li>}
        </ul>

        {isAuthenticated && !crud.isAdding && (
          <button type="button" className="add-card-button" onClick={crud.startAdd}>
            + Add Skill
          </button>
        )}
      </div>
    </section>
  );
}
