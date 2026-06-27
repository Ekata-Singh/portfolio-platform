package com.ektasingh.portfolio.skill.mapper;

import com.ektasingh.portfolio.skill.dto.request.SkillCreateRequest;
import com.ektasingh.portfolio.skill.dto.response.SkillResponse;
import com.ektasingh.portfolio.skill.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public Skill toEntity(SkillCreateRequest request) {

        return Skill.builder()
                .skillName(request.getSkillName())
                .category(request.getCategory())
                .proficiency(request.getProficiency())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public SkillResponse toResponse(Skill skill) {

        return SkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .category(skill.getCategory())
                .proficiency(skill.getProficiency())
                .displayOrder(skill.getDisplayOrder())
                .createdAt(skill.getCreatedAt())
                .updatedAt(skill.getUpdatedAt())
                .build();
    }

    public void updateEntity(Skill skill, SkillCreateRequest request) {

        skill.setSkillName(request.getSkillName());
        skill.setCategory(request.getCategory());
        skill.setProficiency(request.getProficiency());
        skill.setDisplayOrder(request.getDisplayOrder());
    }
}