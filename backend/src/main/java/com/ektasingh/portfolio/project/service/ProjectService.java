package com.ektasingh.portfolio.project.service;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.project.dto.request.ProjectCreateRequest;
import com.ektasingh.portfolio.project.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectCreateRequest request);

    ProjectResponse getProjectById(Long id);

    List<ProjectResponse> getAllProjects();

    PageResponse<ProjectResponse> getProjects(int page, int size);

    ProjectResponse updateProject(Long id, ProjectCreateRequest request);

    void deleteProject(Long id);
}