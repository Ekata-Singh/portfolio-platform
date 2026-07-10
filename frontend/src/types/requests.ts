export type ProjectRequest = {
  projectName: string;
  description: string;
  technologies: string;
  thumbnailUrl: string;
  projectUrl: string;
  githubUrl: string;
  displayOrder: number;
};

export type BlogRequest = {
  title: string;
  slug: string;
  summary: string;
  content: string;
  coverImageUrl: string;
  published: boolean;
  displayOrder: number;
};

export type SkillRequest = {
  skillName: string;
  category: string;
  proficiency: string;
  displayOrder: number;
};

export type TechnologyRequest = {
  technologyName: string;
  category: string;
  iconUrl: string;
  proficiency: string;
  displayOrder: number;
};

export type CertificationRequest = {
  certificationName: string;
  issuingOrganization: string;
  issueDate: string;
  expiryDate: string;
  credentialId: string;
  credentialUrl: string;
  type: string;
  category: string;
  thumbnailUrl: string;
  displayOrder: number;
};

export type AchievementRequest = {
  title: string;
  organization: string;
  achievementDate: string;
  description: string;
  achievementUrl: string;
  category: string;
  status: string;
  certificateFileUrl: string;
  displayOrder: number;
};

export type PublicationRequest = {
  title: string;
  publisher: string;
  publicationDate: string;
  publicationUrl: string;
  description: string;
  tags: string;
  thumbnailUrl: string;
  displayOrder: number;
};

export type ContactRequest = {
  email: string;
  phone: string;
  address: string;
  city: string;
  state: string;
  country: string;
  postalCode: string;
};

export type EducationRequest = {
  institution: string;
  degree: string;
  fieldOfStudy: string;
  startYear: number;
  endYear: number;
  grade: string;
  description: string;
  subjects: string;
};

export type ExperienceRequest = {
  company: string;
  jobTitle: string;
  employmentType: string;
  location: string;
  startDate: string;
  endDate: string;
  currentlyWorking: boolean;
  description: string;
  technologies: string;
  featured: boolean;
};

export type ProfileRequest = {
  fullName: string;
  headline: string;
  email: string;
  about: string;
  phone: string;
  location: string;
  githubUrl: string;
  profileImageUrl: string;
  linkedinUrl: string;
  resumeUrl: string;
  codeforcesUrl: string;
  leetcodeUrl: string;
};
