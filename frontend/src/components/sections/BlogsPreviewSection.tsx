import { Link } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { useInlineCrud } from '../../hooks/useInlineCrud';
import { useSectionExpand } from '../../hooks/useSectionExpand';
import { createBlog, updateBlog, deleteBlog } from '../../services/blogAdminService';
import type { Blog } from '../../types/portfolio';
import type { BlogRequest } from '../../types/requests';

interface BlogsPreviewSectionProps {
  blogs: Blog[];
}

const emptyForm: BlogRequest = {
  title: '',
  slug: '',
  summary: '',
  content: '',
  coverImageUrl: '',
  published: false,
  displayOrder: 0,
};

export function BlogsPreviewSection({ blogs }: BlogsPreviewSectionProps) {
  const { isAuthenticated } = useAuth();
  const [isExpanded, setIsExpanded] = useSectionExpand('blogs');

  const visible = isAuthenticated
    ? [...blogs].sort((a, b) => a.displayOrder - b.displayOrder)
    : [...blogs]
        .filter((blog) => blog.published)
        .sort((a, b) => a.displayOrder - b.displayOrder)
        .slice(0, 3);

  const crud = useInlineCrud<Blog, BlogRequest>({
    service: { create: createBlog, update: updateBlog, remove: deleteBlog },
    emptyForm,
    toFormValues: (b) => ({
      title: b.title,
      slug: b.slug,
      summary: b.summary ?? '',
      content: b.content,
      coverImageUrl: b.coverImageUrl ?? '',
      published: b.published,
      displayOrder: b.displayOrder,
    }),
    getId: (b) => b.id,
    getDisplayName: (b) => b.title,
  });

  if (visible.length === 0 && !isAuthenticated) {
    return null;
  }

  const renderForm = (key: string) => (
    <form className="inline-edit-form" onSubmit={crud.handleSubmit} key={key}>
      {crud.fieldErrors._general && <p className="form-error">{crud.fieldErrors._general}</p>}

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
        Slug
        <input
          value={crud.form.slug}
          onChange={(e) => crud.handleChange('slug', e.target.value)}
          placeholder="my-post-slug"
          required
        />
        {crud.fieldErrors.slug && <span className="form-error">{crud.fieldErrors.slug}</span>}
      </label>

      <label>
        Summary
        <textarea
          value={crud.form.summary}
          onChange={(e) => crud.handleChange('summary', e.target.value)}
        />
      </label>

      <label>
        Content
        <textarea
          value={crud.form.content}
          onChange={(e) => crud.handleChange('content', e.target.value)}
          required
        />
        {crud.fieldErrors.content && (
          <span className="form-error">{crud.fieldErrors.content}</span>
        )}
      </label>

      <label>
        Cover Image URL
        <input
          value={crud.form.coverImageUrl}
          onChange={(e) => crud.handleChange('coverImageUrl', e.target.value)}
          placeholder="https://..."
        />
        {crud.fieldErrors.coverImageUrl && (
          <span className="form-error">{crud.fieldErrors.coverImageUrl}</span>
        )}
      </label>

      <label>
        <input
          type="checkbox"
          checked={crud.form.published}
          onChange={(e) => crud.handleChange('published', e.target.checked)}
        />
        {' '}Published
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
    <section id="blogs" className="section">
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
        <h2>Blogs</h2>
        <span className={isExpanded ? 'section-chevron open' : 'section-chevron'}>
          &#9662;
        </span>
      </div>

      <div className={isExpanded ? 'section-body expanded' : 'section-body'}>
        <div className="card-grid">
          {visible.map((blog) =>
            crud.editingId === blog.id ? (
              renderForm(`edit-${blog.id}`)
            ) : (
              <div
                className={isAuthenticated ? 'card card-editable' : 'card'}
                key={blog.id}
              >
                {isAuthenticated ? (
                  <>
                    <div className="card-edit-actions">
                      <button
                        type="button"
                        onClick={() => crud.startEdit(blog)}
                        aria-label="Edit blog"
                      >
                        &#9998;
                      </button>
                      <button
                        type="button"
                        onClick={() => crud.handleDelete(blog)}
                        aria-label="Delete blog"
                      >
                        &times;
                      </button>
                    </div>
                    {blog.coverImageUrl && (
                      <img
                        src={blog.coverImageUrl}
                        alt={blog.title}
                        className="card-image"
                        loading="lazy"
                      />
                    )}
                    <h3>{blog.title}</h3>
                    {!blog.published && <p className="meta">Draft</p>}
                    {blog.summary && <p>{blog.summary}</p>}
                  </>
                ) : (
                  <Link to={`/blogs/${blog.slug}`} className="card-link-overlay">
                    {blog.coverImageUrl && (
                      <img
                        src={blog.coverImageUrl}
                        alt={blog.title}
                        className="card-image"
                        loading="lazy"
                      />
                    )}
                    <h3>{blog.title}</h3>
                    {blog.summary && <p>{blog.summary}</p>}
                  </Link>
                )}
              </div>
            ),
          )}

          {isAuthenticated && crud.isAdding && renderForm('add-new')}
        </div>

        <Link to="/blogs">View all posts &rarr;</Link>

        {isAuthenticated && !crud.isAdding && (
          <button type="button" className="add-card-button" onClick={crud.startAdd}>
            + Add Blog
          </button>
        )}
      </div>
    </section>
  );
}
