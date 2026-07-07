import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { usePortfolio } from '../hooks/usePortfolio';
import { HeroSection } from '../components/sections/HeroSection';
import { AboutSection } from '../components/sections/AboutSection';
import { EducationSection } from '../components/sections/EducationSection';
import { Experience } from './Experience';
import { ProjectsSection } from '../components/sections/ProjectsSection';
import { AchievementsSection } from '../components/sections/AchievementsSection';
import { PublicationsSection } from '../components/sections/PublicationsSection';
import { CertificatesSection } from '../components/sections/CertificatesSection';

export function Home() {
  const { data } = usePortfolio();
  const location = useLocation();
  const profile = data?.profile;

  useEffect(() => {
    if (location.hash) {
      const target = document.querySelector(location.hash);
      target?.scrollIntoView({ behavior: 'smooth', block: 'start' });
    } else {
      window.scrollTo({ top: 0 });
    }
  }, [location.hash]);

  useEffect(() => {
    const elements = document.querySelectorAll('.hero, .section');

    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add('in-view');
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.12 },
    );

    elements.forEach((el) => observer.observe(el));

    return () => observer.disconnect();
  }, [data]);

  return (
    <div className="home-page">
      <div className="intro-grid">
        <HeroSection profile={profile} />
        <AboutSection profile={profile} />
      </div>

      <EducationSection education={data?.education ?? []} />

      <Experience />

      <ProjectsSection projects={data?.projects ?? []} />

      <AchievementsSection achievements={data?.achievements ?? []} />

      <PublicationsSection publications={data?.publications ?? []} />

      <CertificatesSection certifications={data?.certifications ?? []} />
    </div>
  );
}
