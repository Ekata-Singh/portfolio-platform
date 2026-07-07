package com.ektasingh.portfolio.experience.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.experience.dto.request.ExperienceCreateRequest;
import com.ektasingh.portfolio.experience.dto.response.ExperienceResponse;
import com.ektasingh.portfolio.experience.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/experience")
@RequiredArgsConstructor
@Tag(name = "Experience", description = "Work Experience Management APIs")
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Experience")
    public ExperienceResponse createExperience(
            @Valid @RequestBody ExperienceCreateRequest request) {

        return experienceService.createExperience(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Experience by ID")
    public ExperienceResponse getExperienceById(
            @PathVariable Long id) {

        return experienceService.getExperienceById(id);
    }

    @GetMapping
    @Operation(summary = "Get All Experience Entries")
    public List<ExperienceResponse> getAllExperiences() {

        return experienceService.getAllExperiences();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Experience")
    public ExperienceResponse updateExperience(
            @PathVariable Long id,
            @Valid @RequestBody ExperienceCreateRequest request) {

        return experienceService.updateExperience(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Experience")
    public void deleteExperience(
            @PathVariable Long id) {

        experienceService.deleteExperience(id);
    }
    @GetMapping("/page")
    @Builder
    @Operation(summary = "Get Paginated Experience Entries")
    public ResponseEntity<PageResponse<ExperienceResponse>> getExperiences(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "6") int size) {

        return ResponseEntity.ok(

                experienceService.getExperiences(page, size)

        );
    }
}