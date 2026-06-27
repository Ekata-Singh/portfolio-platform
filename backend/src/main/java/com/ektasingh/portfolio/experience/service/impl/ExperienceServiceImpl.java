package com.ektasingh.portfolio.experience.service.impl;

import com.ektasingh.portfolio.experience.dto.request.ExperienceCreateRequest;
import com.ektasingh.portfolio.experience.dto.response.ExperienceResponse;
import com.ektasingh.portfolio.experience.entity.Experience;
import com.ektasingh.portfolio.experience.exception.ExperienceNotFoundException;
import com.ektasingh.portfolio.experience.mapper.ExperienceMapper;
import com.ektasingh.portfolio.experience.repository.ExperienceRepository;
import com.ektasingh.portfolio.experience.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Override
    public ExperienceResponse createExperience(
            ExperienceCreateRequest request) {

        Experience experience = ExperienceMapper.toEntity(request);

        Experience savedExperience =
                experienceRepository.save(experience);

        return ExperienceMapper.toResponse(savedExperience);
    }

    @Override
    public ExperienceResponse getExperienceById(Long id) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() ->
                        new ExperienceNotFoundException(
                                "Experience not found with id: " + id));

        return ExperienceMapper.toResponse(experience);
    }

    @Override
    public List<ExperienceResponse> getAllExperiences() {

        return experienceRepository.findAll()
                .stream()
                .map(ExperienceMapper::toResponse)
                .toList();
    }

    @Override
    public ExperienceResponse updateExperience(
            Long id,
            ExperienceCreateRequest request) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() ->
                        new ExperienceNotFoundException(
                                "Experience not found with id: " + id));

        experience.setCompany(request.getCompany());
        experience.setJobTitle(request.getJobTitle());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setLocation(request.getLocation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrentlyWorking(request.getCurrentlyWorking());
        experience.setDescription(request.getDescription());

        Experience updatedExperience =
                experienceRepository.save(experience);

        return ExperienceMapper.toResponse(updatedExperience);
    }

    @Override
    public void deleteExperience(Long id) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() ->
                        new ExperienceNotFoundException(
                                "Experience not found with id: " + id));

        experienceRepository.delete(experience);
    }
}