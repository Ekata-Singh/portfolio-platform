package com.ektasingh.portfolio.experience.service;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.experience.dto.request.ExperienceCreateRequest;
import com.ektasingh.portfolio.experience.dto.response.ExperienceResponse;

import java.util.List;

public interface ExperienceService {

    ExperienceResponse createExperience(ExperienceCreateRequest request);

    ExperienceResponse getExperienceById(Long id);

    List<ExperienceResponse> getAllExperiences();

    ExperienceResponse updateExperience(Long id,
                                        ExperienceCreateRequest request);
    
    PageResponse<ExperienceResponse> getExperiences(int page, int size);

    void deleteExperience(Long id);
}