package com.ektasingh.portfolio.experience.service;

import com.ektasingh.portfolio.experience.dto.request.ExperienceCreateRequest;
import com.ektasingh.portfolio.experience.dto.response.ExperienceResponse;

import java.util.List;

public interface ExperienceService {

    ExperienceResponse createExperience(ExperienceCreateRequest request);

    ExperienceResponse getExperienceById(Long id);

    List<ExperienceResponse> getAllExperiences();

    ExperienceResponse updateExperience(Long id,
                                        ExperienceCreateRequest request);

    void deleteExperience(Long id);
}