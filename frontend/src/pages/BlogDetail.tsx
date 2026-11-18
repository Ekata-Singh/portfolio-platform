import { useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import Markdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import { usePortfolio } from '../hooks/usePortfolio';
import { useAuth } from '../hooks/useAuth';
import { resolveAssetUrl } from '../utils/resolveAssetUrl';

const formatDate = (value: string | null) =>
  value
    ? new Date(value).toLocaleDateString(undefined, {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      })
    : null;

const readingTime = (content: string) =>
  Math.max(1, Math.round(content.trim().split(/\s+/).length / 200));

export function BlogDetail() {
  const { slug } = useParams<{ slug: string }>();
  const { data } = usePortfolio();
  const { isAuthenticated } = useAuth();

  const blog = data?.blogs.find((b) => b.slug === slug && (b.published || isAuthenticated));

  useEffect(() => {
    window.scrollTo({ top: 0 });
  }, [slug]);

  if (!blog) {
    return (
      <div className="blog-detail-page section">
        <p>Blog post not found.</p>
        <Link to="/blogs">&larr; Back to Blogs</Link>
      </div>
    );
  }

  const cover = resolveAssetUrl(blog.coverImageUrl);
  const date = formatDate(blog.publishedAt ?? blog.createdAt);

  return (
    <article className="blog-detail-page section">
      <Link to="/blogs">&larr; Back to Blogs</Link>
      <h1>{blog.title}</h1>
      <p className="blog-meta">
        {date && <time dateTime={blog.publishedAt ?? blog.createdAt}>{date}</time>}
        <span aria-hidden="true"> · </span>
        {readingTime(blog.content)} min read
        {!blog.published && <span className="blog-draft-badge">Draft</span>}
      </p>
      {cover && <img src={cover} alt="" />}
      <div className="blog-content markdown-body">
        <Markdown
          remarkPlugins={[remarkGfm]}
          components={{
            a: ({ href, children }) => (
              <a href={href} target="_blank" rel="noreferrer">
                {children}
              </a>
            ),
          }}
        >
          {blog.content}
        </Markdown>
      </div>
    </article>
  );
}
