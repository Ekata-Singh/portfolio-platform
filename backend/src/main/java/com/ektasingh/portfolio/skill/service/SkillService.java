package com.ektasingh.portfolio.skill.service;

import com.ektasingh.portfolio.skill.dto.request.SkillCreateRequest;
import com.ektasingh.portfolio.skill.dto.response.SkillResponse;

import java.util.List;

public interface SkillService {

    SkillResponse createSkill(SkillCreateRequest request);

    SkillResponse getSkillById(Long id);

    List<SkillResponse> getAllSkills();

    SkillResponse updateSkill(Long id, SkillCreateRequest request);

    void deleteSkill(Long id);
}