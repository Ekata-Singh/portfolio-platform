package com.ektasingh.portfolio.education.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponse {

    private Long id;

    private String institution;

    private String degree;

    private String fieldOfStudy;

    private Integer startYear;

    private Integer endYear;

    private String grade;

    private String description;

    private String subjects;

    private String createdAt;

    private String updatedAt;
}