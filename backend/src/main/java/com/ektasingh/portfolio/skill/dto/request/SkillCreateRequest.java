package com.ektasingh.portfolio.skill.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SkillCreateRequest {

    @NotBlank(message = "Skill name is required")
    private String skillName;

    private String category;

    private String proficiency;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}