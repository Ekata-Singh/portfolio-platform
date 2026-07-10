package com.ektasingh.portfolio.experience.mapper;

import com.ektasingh.portfolio.experience.dto.request.ExperienceCreateRequest;
import com.ektasingh.portfolio.experience.dto.response.ExperienceResponse;
import com.ektasingh.portfolio.experience.entity.Experience;

public class ExperienceMapper {

    private ExperienceMapper() {
    }

    public static Experience toEntity(ExperienceCreateRequest request) {

        return Experience.builder()
                .company(request.getCompany())
                .jobTitle(request.getJobTitle())
                .employmentType(request.getEmploymentType())
                .location(request.getLocation())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .currentlyWorking(request.getCurrentlyWorking())
                .description(request.getDescription())
                .technologies(request.getTechnologies())
                .featured(request.getFeatured() != null && request.getFeatured())
                .build();
    }

    public static ExperienceResponse toResponse(Experience experience) {

        return ExperienceResponse.builder()
                .id(experience.getId())
                .company(experience.getCompany())
                .jobTitle(experience.getJobTitle())
                .employmentType(experience.getEmploymentType())
                .location(experience.getLocation())
                .startDate(
                        experience.getStartDate() != null
                                ? experience.getStartDate().toString()
                                : null
                )
                .endDate(
                        experience.getEndDate() != null
                                ? experience.getEndDate().toString()
                                : null
                )
                .currentlyWorking(experience.getCurrentlyWorking())
                .description(experience.getDescription())
                .technologies(experience.getTechnologies())
                .featured(experience.getFeatured())
                .createdAt(
                        experience.getCreatedAt() != null
                                ? experience.getCreatedAt().toString()
                                : null
                )
                .updatedAt(
                        experience.getUpdatedAt() != null
                                ? experience.getUpdatedAt().toString()
                                : null
                )
                .build();
    }
}