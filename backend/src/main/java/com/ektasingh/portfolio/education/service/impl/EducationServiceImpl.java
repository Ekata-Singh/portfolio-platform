package com.ektasingh.portfolio.education.service.impl;

import com.ektasingh.portfolio.education.dto.request.EducationCreateRequest;
import com.ektasingh.portfolio.education.dto.response.EducationResponse;
import com.ektasingh.portfolio.education.entity.Education;
import com.ektasingh.portfolio.education.exception.EducationNotFoundException;
import com.ektasingh.portfolio.education.mapper.EducationMapper;
import com.ektasingh.portfolio.education.repository.EducationRepository;
import com.ektasingh.portfolio.education.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Override
    public EducationResponse createEducation(EducationCreateRequest request) {

        Education education = EducationMapper.toEntity(request);

        Education savedEducation = educationRepository.save(education);

        return EducationMapper.toResponse(savedEducation);
    }

    @Override
    public EducationResponse getEducationById(Long id) {

        Education education = educationRepository.findById(id)
                .orElseThrow(() ->
                        new EducationNotFoundException("Education not found with id: " + id));

        return EducationMapper.toResponse(education);
    }

    @Override
    public List<EducationResponse> getAllEducations() {

        return educationRepository.findAll()
                .stream()
                .map(EducationMapper::toResponse)
                .toList();
    }

    @Override
    public EducationResponse updateEducation(Long id,
                                             EducationCreateRequest request) {

        Education education = educationRepository.findById(id)
                .orElseThrow(() ->
                        new EducationNotFoundException("Education not found with id: " + id));

        education.setInstitution(request.getInstitution());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartYear(request.getStartYear());
        education.setEndYear(request.getEndYear());
        education.setGrade(request.getGrade());
        education.setDescription(request.getDescription());

        Education updatedEducation = educationRepository.save(education);

        return EducationMapper.toResponse(updatedEducation);
    }

    @Override
    public void deleteEducation(Long id) {

        Education education = educationRepository.findById(id)
                .orElseThrow(() ->
                        new EducationNotFoundException("Education not found with id: " + id));

        educationRepository.delete(education);
    }
}