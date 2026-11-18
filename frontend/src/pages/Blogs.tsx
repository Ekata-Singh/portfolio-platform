import { Link } from 'react-router-dom';
import { usePortfolio } from '../hooks/usePortfolio';
import { useAuth } from '../hooks/useAuth';
import { resolveAssetUrl } from '../utils/resolveAssetUrl';

export function Blogs() {
  const { data } = usePortfolio();
  const { isAuthenticated } = useAuth();

  const blogs = [...(data?.blogs ?? [])]
    .filter((blog) => blog.published || isAuthenticated)
    .sort((a, b) => a.displayOrder - b.displayOrder);

  return (
    <div className="blogs-page section">
      <h1>Blogs</h1>
      {blogs.length === 0 ? (
        <p>No posts yet — check back soon.</p>
      ) : (
        <div className="card-grid">
          {blogs.map((blog) => {
            const cover = resolveAssetUrl(blog.coverImageUrl);
            return (
              <Link to={`/blogs/${blog.slug}`} className="card" key={blog.id}>
                {cover && <img src={cover} alt="" className="card-image" loading="lazy" />}
                <h3>
                  {blog.title}
                  {!blog.published && <span className="blog-draft-badge">Draft</span>}
                </h3>
                {blog.summary && <p>{blog.summary}</p>}
              </Link>
            );
          })}
        </div>
      )}
    </div>
  );
}
