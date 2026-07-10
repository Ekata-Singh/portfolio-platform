package com.ektasingh.portfolio.portfolio.service.impl;

import com.ektasingh.portfolio.achievement.service.AchievementService;
import com.ektasingh.portfolio.blog.service.BlogService;
import com.ektasingh.portfolio.certificate.service.CertificationService;
import com.ektasingh.portfolio.contact.service.ContactService;
import com.ektasingh.portfolio.education.service.EducationService;
import com.ektasingh.portfolio.experience.service.ExperienceService;
import com.ektasingh.portfolio.portfolio.dto.response.PortfolioResponse;
import com.ektasingh.portfolio.portfolio.service.PortfolioService;
import com.ektasingh.portfolio.profile.service.ProfileService;
import com.ektasingh.portfolio.project.service.ProjectService;
import com.ektasingh.portfolio.publication.service.PublicationService;
import com.ektasingh.portfolio.skill.service.SkillService;
import com.ektasingh.portfolio.technology.service.TechnologyService;
import org.springframework.stereotype.Service;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final ProfileService profileService;
    private final ContactService contactService;
    private final EducationService educationService;
    private final ExperienceService experienceService;
    private final ProjectService projectService;
    private final SkillService skillService;
    private final TechnologyService technologyService;
    private final CertificationService certificationService;
    private final PublicationService publicationService;
    private final AchievementService achievementService;
    private final BlogService blogService;

    public PortfolioServiceImpl(
            ProfileService profileService,
            ContactService contactService,
            EducationService educationService,
            ExperienceService experienceService,
            ProjectService projectService,
            SkillService skillService,
            TechnologyService technologyService,
            CertificationService certificationService,
            PublicationService publicationService,
            AchievementService achievementService,
            BlogService blogService) {

        this.profileService = profileService;
        this.contactService = contactService;
        this.educationService = educationService;
        this.experienceService = experienceService;
        this.projectService = projectService;
        this.skillService = skillService;
        this.technologyService = technologyService;
        this.certificationService = certificationService;
        this.publicationService = publicationService;
        this.achievementService = achievementService;
        this.blogService = blogService;
    }

    @Override
    public PortfolioResponse getPortfolio() {

        PortfolioResponse response = new PortfolioResponse();

        /*
         * Since there is only one Profile and one Contact,
         * we'll fetch the first record for now.
         */

        response.setProfile(
                profileService.getAllProfiles().stream().findFirst().orElse(null)
        );

        response.setContact(
                contactService.getAllContacts().stream().findFirst().orElse(null)
        );

        response.setEducation(
                educationService.getAllEducations()
        );

        response.setExperience(
                experienceService.getAllExperiences()
        );

        response.setProjects(
                projectService.getAllProjects()
        );

        response.setSkills(
                skillService.getAllSkills()
        );

        response.setTechnologies(
                technologyService.getAllTechnologies()
        );

        response.setCertifications(
                certificationService.getAllCertifications()
        );

        response.setPublications(
                publicationService.getAllPublications()
        );

        response.setAchievements(
                achievementService.getAllAchievements()
        );

        response.setBlogs(
                blogService.getAllBlogs()
        );

        return response;
    }
}