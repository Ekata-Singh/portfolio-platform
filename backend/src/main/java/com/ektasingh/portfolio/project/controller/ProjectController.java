package com.ektasingh.portfolio.project.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.project.dto.request.ProjectCreateRequest;
import com.ektasingh.portfolio.project.dto.response.ProjectResponse;
import com.ektasingh.portfolio.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Projects", description = "Project Management APIs")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Project")
    public ProjectResponse createProject(
            @Valid @RequestBody ProjectCreateRequest request) {

        return projectService.createProject(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Project by ID")
    public ProjectResponse getProject(
            @PathVariable Long id) {

        return projectService.getProjectById(id);
    }

    @GetMapping
    @Operation(summary = "Get All Projects")
    public List<ProjectResponse> getAllProjects() {

        return projectService.getAllProjects();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Project")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectCreateRequest request) {

        return projectService.updateProject(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Project")
    public void deleteProject(
            @PathVariable Long id) {

        projectService.deleteProject(id);
    }

    @GetMapping("/page")
    @Operation(summary = "Get Paginated Projects")
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

    @PostMapping(
        value = "/{id}/thumbnail",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Upload Project Thumbnail")
    public ResponseEntity<ProjectResponse> uploadThumbnail(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                projectService.uploadThumbnail(id, file)
        );
    }

    @GetMapping("/ping")
    public String ping() {
        return "Project Controller Working";
    }
}