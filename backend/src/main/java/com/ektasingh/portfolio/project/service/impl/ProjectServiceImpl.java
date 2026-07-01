package com.ektasingh.portfolio.project.service.impl;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.project.dto.request.ProjectCreateRequest;
import com.ektasingh.portfolio.project.dto.response.ProjectResponse;
import com.ektasingh.portfolio.project.entity.Project;
import com.ektasingh.portfolio.project.exception.ProjectNotFoundException;
import com.ektasingh.portfolio.project.mapper.ProjectMapper;
import com.ektasingh.portfolio.project.repository.ProjectRepository;
import com.ektasingh.portfolio.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final ProjectMapper mapper;

    @Override
    public ProjectResponse createProject(ProjectCreateRequest request) {

        Project project = mapper.toEntity(request);

        Project savedProject = repository.save(project);

        return mapper.toResponse(savedProject);
    }

    @Override
    public ProjectResponse getProjectById(Long id) {

        Project project = repository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        return mapper.toResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    
    @Override
    public PageResponse<ProjectResponse> getProjects(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Project> projectPage =
                repository.findAllByOrderByDisplayOrderAsc(pageable);

        PageResponse<ProjectResponse> response = new PageResponse<>();

        response.setContent(
                projectPage.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList());

        response.setPage(projectPage.getNumber());

        response.setSize(projectPage.getSize());

        response.setTotalElements(projectPage.getTotalElements());

        response.setTotalPages(projectPage.getTotalPages());

        response.setFirst(projectPage.isFirst());

        response.setLast(projectPage.isLast());

        response.setEmpty(projectPage.isEmpty());

        return response;
    }

    @Override
    public ProjectResponse updateProject(Long id,
                                         ProjectCreateRequest request) {

        Project project = repository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        project.setTechnologies(request.getTechnologies());
        project.setProjectUrl(request.getProjectUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setDisplayOrder(request.getDisplayOrder());

        Project updatedProject = repository.save(project);

        return mapper.toResponse(updatedProject);
    }

    @Override
    public void deleteProject(Long id) {

        Project project = repository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        repository.delete(project);
    }
}