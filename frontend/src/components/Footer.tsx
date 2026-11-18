import { Link } from 'react-router-dom';
import { usePortfolio } from '../hooks/usePortfolio';

export function Footer() {
  const { data } = usePortfolio();
  const profile = data?.profile;
  const email = data?.contact?.email ?? profile?.email;

  const links = [
    { href: profile?.githubUrl, label: 'GitHub' },
    { href: profile?.linkedinUrl, label: 'LinkedIn' },
    { href: profile?.leetcodeUrl, label: 'LeetCode' },
    { href: profile?.codeforcesUrl, label: 'Codeforces' },
    { href: email ? `mailto:${email}` : null, label: 'Email' },
  ].filter((link): link is { href: string; label: string } => Boolean(link.href));

  return (
    <footer className="footer">
      <div className="footer-inner">
        <div className="footer-brand">
          <Link to="/" className="footer-name">
            {profile?.fullName ?? 'Portfolio'}
          </Link>
          {profile?.headline && <span className="footer-headline">{profile.headline}</span>}
        </div>

        {links.length > 0 && (
          <nav className="footer-links" aria-label="Social links">
            {links.map((link) => (
              <a
                key={link.label}
                href={link.href}
                target={link.href.startsWith('mailto:') ? undefined : '_blank'}
                rel="noreferrer"
              >
                {link.label}
              </a>
            ))}
          </nav>
        )}

        <button
          type="button"
          className="footer-top"
          onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}
        >
          Back to top &uarr;
        </button>
      </div>

      <p className="footer-copy">
        &copy; {new Date().getFullYear()}
        {profile?.fullName ? ` ${profile.fullName}` : ''}. All rights reserved.
      </p>
    </footer>
  );
}
