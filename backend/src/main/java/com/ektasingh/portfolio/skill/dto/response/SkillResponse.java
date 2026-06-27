package com.ektasingh.portfolio.skill.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SkillResponse {

    private Long id;

    private String skillName;

    private String category;

    private String proficiency;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}