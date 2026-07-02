package com.ektasingh.portfolio.project.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.project.dto.request.ProjectCreateRequest;
import com.ektasingh.portfolio.project.dto.response.ProjectResponse;
import com.ektasingh.portfolio.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(
            @Valid @RequestBody ProjectCreateRequest request) {

        return projectService.createProject(request);
    }

    @GetMapping("/{id}")
    public ProjectResponse getProject(
            @PathVariable Long id) {

        return projectService.getProjectById(id);
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {

        return projectService.getAllProjects();
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectCreateRequest request) {

        return projectService.updateProject(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
            @PathVariable Long id) {

        projectService.deleteProject(id);
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponse<ProjectResponse>> getProjects(

            @RequestParam(required = false) String query,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "6") int size,

            @RequestParam(defaultValue = "displayOrder") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(

                projectService.getProjects(
                        query,
                        page,
                        size,
                        sortBy,
                        direction)

        );
    }

    @GetMapping("/ping")
    public String ping() {
        return "Project Controller Working";
    }
}