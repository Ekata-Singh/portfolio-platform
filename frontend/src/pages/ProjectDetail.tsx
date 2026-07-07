import { useParams, Link } from 'react-router-dom';
import { usePortfolio } from '../hooks/usePortfolio';
import { getTechList } from '../utils/project';
import { resolveAssetUrl } from '../utils/resolveAssetUrl';

export function ProjectDetail() {
  const { id } = useParams<{ id: string }>();
  const { data } = usePortfolio();

  const project = data?.projects.find((p) => String(p.id) === id);

  if (!project) {
    return (
      <div className="project-detail-page section">
        <p>Project not found.</p>
        <Link to="/projects">Back to Projects</Link>
      </div>
    );
  }

  const techList = getTechList(project.technologies);
  const thumbnailUrl = resolveAssetUrl(project.thumbnailUrl);

  return (
    <article className="project-detail-page section">
      <Link to="/projects">&larr; Back to Projects</Link>
      <h1>{project.projectName}</h1>

      {thumbnailUrl && (
        <img src={thumbnailUrl} alt={project.projectName} className="project-detail-image" />
      )}

      {techList.length > 0 && (
        <div className="tech-stack-block">
          <h4>Tech Stack</h4>
          <ul className="tech-badge-list">
            {techList.map((tech) => (
              <li key={tech}>{tech}</li>
            ))}
          </ul>
        </div>
      )}

      <div className="card-links">
        {project.projectUrl && (
          <a href={project.projectUrl} target="_blank" rel="noreferrer">
            Live Demo
          </a>
        )}
        {project.githubUrl && (
          <a href={project.githubUrl} target="_blank" rel="noreferrer">
            GitHub
          </a>
        )}
      </div>

      <div className="project-description">{project.description}</div>
    </article>
  );
}
