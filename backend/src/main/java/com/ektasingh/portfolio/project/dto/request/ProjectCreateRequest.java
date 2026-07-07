package com.ektasingh.portfolio.project.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateRequest {

    @NotBlank(message = "Project name is required")
    private String projectName;

    private String description;

    private String technologies;

    @Pattern(
            regexp = "^$|^(https?://).*$|^project/.*$",
            message = "Thumbnail URL must be a valid http(s) URL")
    private String thumbnailUrl;

    @Pattern(regexp = "^$|^(https?://).*$", message = "Project URL must be a valid http(s) URL")
    private String projectUrl;

    @Pattern(regexp = "^$|^(https?://).*$", message = "GitHub URL must be a valid http(s) URL")
    private String githubUrl;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be zero or positive")
    private Integer displayOrder;
}