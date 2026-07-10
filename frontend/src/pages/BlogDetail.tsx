import { useParams, Link } from 'react-router-dom';
import { usePortfolio } from '../hooks/usePortfolio';

export function BlogDetail() {
  const { slug } = useParams<{ slug: string }>();
  const { data } = usePortfolio();

  const blog = data?.blogs.find((b) => b.slug === slug && b.published);

  if (!blog) {
    return (
      <div className="blog-detail-page section">
        <p>Blog post not found.</p>
        <Link to="/blogs">Back to Blogs</Link>
      </div>
    );
  }

  return (
    <article className="blog-detail-page section">
      <Link to="/blogs">&larr; Back to Blogs</Link>
      <h1>{blog.title}</h1>
      {blog.coverImageUrl && <img src={blog.coverImageUrl} alt={blog.title} />}
      <div className="blog-content">{blog.content}</div>
    </article>
  );
}
