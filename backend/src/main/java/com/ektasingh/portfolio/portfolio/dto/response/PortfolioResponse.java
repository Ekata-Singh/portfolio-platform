package com.ektasingh.portfolio.portfolio.dto.response;

import com.ektasingh.portfolio.achievement.dto.response.AchievementResponse;
import com.ektasingh.portfolio.blog.dto.response.BlogResponse;
import com.ektasingh.portfolio.certificate.dto.response.CertificationResponse;
import com.ektasingh.portfolio.contact.dto.response.ContactResponse;
import com.ektasingh.portfolio.education.dto.response.EducationResponse;
import com.ektasingh.portfolio.experience.dto.response.ExperienceResponse;
import com.ektasingh.portfolio.profile.dto.response.ProfileResponse;
import com.ektasingh.portfolio.project.dto.response.ProjectResponse;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.skill.dto.response.SkillResponse;
import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioResponse {

    private ProfileResponse profile;

    private ContactResponse contact;

    private List<EducationResponse> education;

    private List<ExperienceResponse> experience;

    private List<ProjectResponse> projects;

    private List<SkillResponse> skills;

    private List<TechnologyResponse> technologies;

    private List<CertificationResponse> certifications;

    private List<PublicationResponse> publications;

    private List<AchievementResponse> achievements;

    private List<BlogResponse> blogs;
}