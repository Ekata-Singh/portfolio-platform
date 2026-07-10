package com.ektasingh.portfolio.skill.controller;

import com.ektasingh.portfolio.skill.dto.request.SkillCreateRequest;
import com.ektasingh.portfolio.skill.dto.response.SkillResponse;
import com.ektasingh.portfolio.skill.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SkillResponse createSkill(
            @Valid @RequestBody SkillCreateRequest request) {

        return service.createSkill(request);
    }

    @GetMapping("/{id}")
    public SkillResponse getSkillById(@PathVariable Long id) {

        return service.getSkillById(id);
    }

    @GetMapping
    public List<SkillResponse> getAllSkills() {

        return service.getAllSkills();
    }

    @PutMapping("/{id}")
    public SkillResponse updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody SkillCreateRequest request) {

        return service.updateSkill(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(@PathVariable Long id) {

        service.deleteSkill(id);
    }
}