import { useState } from 'react';
import { useLocation } from 'react-router-dom';

/**
 * Collapsible section state that auto-expands when the URL hash targets the section
 * (e.g. clicking "Skills" in the navbar navigates to /#skills).
 */
export function useSectionExpand(sectionId: string, defaultExpanded = true) {
  const location = useLocation();
  const target = `#${sectionId}`;
  const [isExpanded, setIsExpanded] = useState(defaultExpanded || location.hash === target);
  const [prevHash, setPrevHash] = useState(location.hash);

  // Adjust state during render when the hash changes (avoids a cascading effect).
  if (location.hash !== prevHash) {
    setPrevHash(location.hash);
    if (location.hash === target) {
      setIsExpanded(true);
    }
  }

  return [isExpanded, setIsExpanded] as const;
}
