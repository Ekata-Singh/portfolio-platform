export interface Profile {
  id: number;
  fullName: string;
  headline: string;
  about: string | null;
  email: string;
  phone: string | null;
  location: string | null;
  githubUrl: string | null;
  profileImageUrl: string | null;
  linkedinUrl: string | null;
  resumeUrl: string | null;
  codeforcesUrl: string | null;
  leetcodeUrl: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface Contact {
  id: number;
  email: string;
  phone: string;
  address: string | null;
  city: string | null;
  state: string | null;
  country: string | null;
  postalCode: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface Education {
  id: number;
  institution: string;
  degree: string;
  fieldOfStudy: string;
  startYear: number;
  endYear: number | null;
  grade: string | null;
  description: string | null;
  subjects: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface Experience {
  id: number;
  company: string;
  jobTitle: string;
  employmentType: string | null;
  location: string | null;
  startDate: string;
  endDate: string | null;
  currentlyWorking: boolean;
  description: string | null;
  technologies: string | null;
  featured: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface Project {
  id: number;
  projectName: string;
  description: string | null;
  technologies: string | null;
  thumbnailUrl: string | null;
  projectUrl: string | null;
  githubUrl: string | null;
  displayOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface Skill {
  id: number;
  skillName: string;
  category: string | null;
  proficiency: string | null;
  displayOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface Technology {
  id: number;
  technologyName: string;
  category: string;
  iconUrl: string | null;
  proficiency: string | null;
  displayOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface Certification {
  id: number;
  certificationName: string;
  issuingOrganization: string;
  issueDate: string | null;
  expiryDate: string | null;
  credentialId: string | null;
  credentialUrl: string | null;
  type: string | null;
  category: string | null;
  thumbnailUrl: string | null;
  displayOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface Publication {
  id: number;
  title: string;
  publisher: string;
  publicationDate: string;
  publicationUrl: string | null;
  description: string | null;
  tags: string | null;
  thumbnailUrl: string | null;
  displayOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface Achievement {
  id: number;
  title: string;
  organization: string | null;
  achievementDate: string | null;
  description: string | null;
  achievementUrl: string | null;
  category: string | null;
  status: string | null;
  certificateFileUrl: string | null;
  displayOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface Blog {
  id: number;
  title: string;
  slug: string;
  summary: string | null;
  content: string;
  coverImageUrl: string | null;
  published: boolean;
  publishedAt: string | null;
  displayOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface PortfolioData {
  profile: Profile | null;
  contact: Contact | null;
  education: Education[];
  experience: Experience[];
  projects: Project[];
  skills: Skill[];
  technologies: Technology[];
  certifications: Certification[];
  publications: Publication[];
  achievements: Achievement[];
  blogs: Blog[];
}
