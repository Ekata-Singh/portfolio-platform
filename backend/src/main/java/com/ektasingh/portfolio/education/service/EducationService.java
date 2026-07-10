package com.ektasingh.portfolio.education.service;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.education.dto.request.EducationCreateRequest;
import com.ektasingh.portfolio.education.dto.response.EducationResponse;

import java.util.List;

public interface EducationService {

    EducationResponse createEducation(EducationCreateRequest request);

    EducationResponse getEducationById(Long id);

    List<EducationResponse> getAllEducations();

    EducationResponse updateEducation(Long id, EducationCreateRequest request);

    PageResponse<EducationResponse> getEducations(int page, int size);
    
    void deleteEducation(Long id);
}