package com.ektasingh.portfolio.education.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.education.dto.request.EducationCreateRequest;
import com.ektasingh.portfolio.education.dto.response.EducationResponse;
import com.ektasingh.portfolio.education.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EducationResponse createEducation(
            @Valid @RequestBody EducationCreateRequest request) {

        return educationService.createEducation(request);
    }

    @GetMapping("/{id}")
    public EducationResponse getEducationById(
            @PathVariable Long id) {

        return educationService.getEducationById(id);
    }

    @GetMapping
    public List<EducationResponse> getAllEducations() {

        return educationService.getAllEducations();
    }

    @PutMapping("/{id}")
    public EducationResponse updateEducation(
            @PathVariable Long id,
            @Valid @RequestBody EducationCreateRequest request) {

        return educationService.updateEducation(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEducation(
            @PathVariable Long id) {

        educationService.deleteEducation(id);
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponse<EducationResponse>> getEducations(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "6") int size) {

        return ResponseEntity.ok(

                educationService.getEducations(page, size)

        );
    }
}