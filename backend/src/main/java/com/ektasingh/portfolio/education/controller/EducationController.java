package com.ektasingh.portfolio.education.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.education.dto.request.EducationCreateRequest;
import com.ektasingh.portfolio.education.dto.response.EducationResponse;
import com.ektasingh.portfolio.education.service.EducationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/education")
@RequiredArgsConstructor
@Tag(name = "Education", description = "Education Management APIs")
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Education")
    public EducationResponse createEducation(
            @Valid @RequestBody EducationCreateRequest request) {

        return educationService.createEducation(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Education by ID")
    public EducationResponse getEducationById(
            @PathVariable Long id) {

        return educationService.getEducationById(id);
    }

    @GetMapping
    @Operation(summary = "Get All Education Entries")
    public List<EducationResponse> getAllEducations() {

        return educationService.getAllEducations();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Education")
    public EducationResponse updateEducation(
            @PathVariable Long id,
            @Valid @RequestBody EducationCreateRequest request) {

        return educationService.updateEducation(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Education")
    public void deleteEducation(
            @PathVariable Long id) {

        educationService.deleteEducation(id);
    }

    @GetMapping("/page")
    @Operation(summary = "Get Paginated Education Entries")
    public ResponseEntity<PageResponse<EducationResponse>> getEducations(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "6") int size) {

        return ResponseEntity.ok(

                educationService.getEducations(page, size)

        );
    }
}