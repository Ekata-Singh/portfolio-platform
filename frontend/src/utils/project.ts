export function getProjectSummary(description: string | null, maxLength = 220): string {
  if (!description) return '';

  const firstParagraph = description.split(/\n\s*\n/)[0].trim();
  if (firstParagraph.length <= maxLength) return firstParagraph;

  const truncated = firstParagraph.slice(0, maxLength);
  const lastSpace = truncated.lastIndexOf(' ');
  return `${truncated.slice(0, lastSpace > 0 ? lastSpace : maxLength).trim()}...`;
}

export function getTechList(technologies: string | null): string[] {
  if (!technologies) return [];
  return technologies
    .split(',')
    .map((t) => t.trim())
    .filter(Boolean);
}
