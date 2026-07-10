import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

export function useSectionExpand(sectionId: string) {
  const location = useLocation();
  const [isExpanded, setIsExpanded] = useState(false);

  useEffect(() => {
    if (location.hash === `#${sectionId}`) {
      setIsExpanded(true);
    }
  }, [location.hash, sectionId]);

  return [isExpanded, setIsExpanded] as const;
}
