package com.ektasingh.portfolio.education.mapper;

import com.ektasingh.portfolio.education.dto.request.EducationCreateRequest;
import com.ektasingh.portfolio.education.dto.response.EducationResponse;
import com.ektasingh.portfolio.education.entity.Education;

public class EducationMapper {

    private EducationMapper() {
    }

    public static Education toEntity(EducationCreateRequest request) {

        return Education.builder()
                .institution(request.getInstitution())
                .degree(request.getDegree())
                .fieldOfStudy(request.getFieldOfStudy())
                .startYear(request.getStartYear())
                .endYear(request.getEndYear())
                .grade(request.getGrade())
                .description(request.getDescription())
                .build();
    }

    public static EducationResponse toResponse(Education education) {

        return EducationResponse.builder()
                .id(education.getId())
                .institution(education.getInstitution())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .startYear(education.getStartYear())
                .endYear(education.getEndYear())
                .grade(education.getGrade())
                .description(education.getDescription())
                .createdAt(
                        education.getCreatedAt() != null
                                ? education.getCreatedAt().toString()
                                : null
                )
                .updatedAt(
                        education.getUpdatedAt() != null
                                ? education.getUpdatedAt().toString()
                                : null
                )
                .build();
    }
}