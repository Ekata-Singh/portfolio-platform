package com.ektasingh.portfolio.project.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectResponse {

    private Long id;

    private String projectName;

    private String description;

    private String technologies;

    private String thumbnailUrl;

    private String projectUrl;

    private String githubUrl;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}