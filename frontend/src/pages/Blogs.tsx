import { Link } from 'react-router-dom';
import { usePortfolio } from '../hooks/usePortfolio';

export function Blogs() {
  const { data } = usePortfolio();

  const blogs = [...(data?.blogs ?? [])]
    .filter((blog) => blog.published)
    .sort((a, b) => a.displayOrder - b.displayOrder);

  return (
    <div className="blogs-page section">
      <h1>Blogs</h1>
      <div className="card-grid">
        {blogs.map((blog) => (
          <Link to={`/blogs/${blog.slug}`} className="card" key={blog.id}>
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
        ))}
      </div>
    </div>
  );
}
