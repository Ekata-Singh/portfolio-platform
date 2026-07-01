package com.ektasingh.portfolio.skill.service;

import com.ektasingh.portfolio.skill.dto.request.SkillCreateRequest;
import com.ektasingh.portfolio.skill.dto.response.SkillResponse;
import com.ektasingh.portfolio.common.dto.response.PageResponse;
import java.util.List;

public interface SkillService {

    SkillResponse createSkill(SkillCreateRequest request);

    SkillResponse getSkillById(Long id);

    List<SkillResponse> getAllSkills();

    PageResponse<SkillResponse> getSkills(int page, int size);

    SkillResponse updateSkill(Long id,
                              SkillCreateRequest request);

    void deleteSkill(Long id);
}