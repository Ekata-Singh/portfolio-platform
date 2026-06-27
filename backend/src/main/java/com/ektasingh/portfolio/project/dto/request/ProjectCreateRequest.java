package com.ektasingh.portfolio.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateRequest {

    @NotBlank(message = "Project name is required")
    private String projectName;

    private String description;

    private String technologies;

    private String projectUrl;

    private String githubUrl;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}