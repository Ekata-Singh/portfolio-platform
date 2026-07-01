package com.ektasingh.portfolio.experience.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.experience.dto.request.ExperienceCreateRequest;
import com.ektasingh.portfolio.experience.dto.response.ExperienceResponse;
import com.ektasingh.portfolio.experience.service.ExperienceService;
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
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExperienceResponse createExperience(
            @Valid @RequestBody ExperienceCreateRequest request) {

        return experienceService.createExperience(request);
    }

    @GetMapping("/{id}")
    public ExperienceResponse getExperienceById(
            @PathVariable Long id) {

        return experienceService.getExperienceById(id);
    }

    @GetMapping
    public List<ExperienceResponse> getAllExperiences() {

        return experienceService.getAllExperiences();
    }

    @PutMapping("/{id}")
    public ExperienceResponse updateExperience(
            @PathVariable Long id,
            @Valid @RequestBody ExperienceCreateRequest request) {

        return experienceService.updateExperience(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExperience(
            @PathVariable Long id) {

        experienceService.deleteExperience(id);
    }
    @GetMapping("/page")
    @Builder
    public ResponseEntity<PageResponse<ExperienceResponse>> getExperiences(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "6") int size) {

        return ResponseEntity.ok(

                experienceService.getExperiences(page, size)

        );
    }
}