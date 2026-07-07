import { Link } from 'react-router-dom';
import { usePortfolio } from '../hooks/usePortfolio';
import { getProjectSummary, getTechList } from '../utils/project';
import { resolveAssetUrl } from '../utils/resolveAssetUrl';

export function Projects() {
  const { data } = usePortfolio();

  const projects = [...(data?.projects ?? [])].sort((a, b) => a.displayOrder - b.displayOrder);

  return (
    <div className="projects-page section">
      <h1>Projects</h1>
      <div className="card-grid">
        {projects.map((project) => {
          const techList = getTechList(project.technologies);
          const thumbnailUrl = resolveAssetUrl(project.thumbnailUrl);

          return (
            <Link to={`/projects/${project.id}`} className="card" key={project.id}>
              {thumbnailUrl && (
                <img
                  src={thumbnailUrl}
                  alt={project.projectName}
                  className="card-image"
                  loading="lazy"
                />
              )}
              <h3>{project.projectName}</h3>
              <p>{getProjectSummary(project.description)}</p>
              {techList.length > 0 && (
                <ul className="tech-badge-list">
                  {techList.map((tech) => (
                    <li key={tech}>{tech}</li>
                  ))}
                </ul>
              )}
              <span className="card-cta">View Details &rarr;</span>
            </Link>
          );
        })}
      </div>
    </div>
  );
}
