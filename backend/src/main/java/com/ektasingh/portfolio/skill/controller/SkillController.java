package com.ektasingh.portfolio.skill.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.skill.dto.request.SkillCreateRequest;
import com.ektasingh.portfolio.skill.dto.response.SkillResponse;
import com.ektasingh.portfolio.skill.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
@Tag(name = "Skills", description = "Skill Management APIs")
public class SkillController {

    private final SkillService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Skill")
    public SkillResponse createSkill(
            @Valid @RequestBody SkillCreateRequest request) {

        return service.createSkill(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Skill by ID")
    public SkillResponse getSkillById(@PathVariable Long id) {

        return service.getSkillById(id);
    }

    @GetMapping
    @Operation(summary = "Get All Skills")
    public List<SkillResponse> getAllSkills() {

        return service.getAllSkills();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Skill")
    public SkillResponse updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody SkillCreateRequest request) {

        return service.updateSkill(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Skill")
    public void deleteSkill(@PathVariable Long id) {

        service.deleteSkill(id);
    }

    @GetMapping("/page")
    @Operation(summary = "Get Paginated Skills")
    public ResponseEntity<PageResponse<SkillResponse>> getSkills(

            @RequestParam(defaultValue = "") String query,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "displayOrder") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                service.getSkills(query, page, size, sortBy, direction));
    }
}