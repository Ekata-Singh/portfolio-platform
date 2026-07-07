package com.ektasingh.portfolio.project.mapper;

import com.ektasingh.portfolio.project.dto.request.ProjectCreateRequest;
import com.ektasingh.portfolio.project.dto.response.ProjectResponse;
import com.ektasingh.portfolio.project.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectCreateRequest request) {

        Project project = new Project();

        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        project.setTechnologies(request.getTechnologies());
        project.setThumbnailUrl(request.getThumbnailUrl());
        project.setProjectUrl(request.getProjectUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setDisplayOrder(request.getDisplayOrder());

        return project;
    }

    public ProjectResponse toResponse(Project project) {

        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setProjectName(project.getProjectName());
        response.setDescription(project.getDescription());
        response.setTechnologies(project.getTechnologies());
        response.setThumbnailUrl(project.getThumbnailUrl());
        response.setProjectUrl(project.getProjectUrl());
        response.setGithubUrl(project.getGithubUrl());
        response.setDisplayOrder(project.getDisplayOrder());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());

        return response;
    }
}