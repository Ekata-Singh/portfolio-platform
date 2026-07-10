package com.ektasingh.portfolio.experience.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExperienceResponse {

    private Long id;

    private String company;

    private String jobTitle;

    private String employmentType;

    private String location;

    private String startDate;

    private String endDate;

    private Boolean currentlyWorking;

    private String description;

    private String technologies;

    private Boolean featured;

    private String createdAt;

    private String updatedAt;
}