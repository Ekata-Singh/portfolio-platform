package com.ektasingh.portfolio.experience.service.impl;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.experience.dto.request.ExperienceCreateRequest;
import com.ektasingh.portfolio.experience.dto.response.ExperienceResponse;
import com.ektasingh.portfolio.experience.entity.Experience;
import com.ektasingh.portfolio.experience.exception.ExperienceNotFoundException;
import com.ektasingh.portfolio.experience.mapper.ExperienceMapper;
import com.ektasingh.portfolio.experience.repository.ExperienceRepository;
import com.ektasingh.portfolio.experience.service.ExperienceService;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
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
        experience.setTechnologies(request.getTechnologies());
        experience.setFeatured(request.getFeatured() != null && request.getFeatured());

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

    @Override
        public PageResponse<ExperienceResponse> getExperiences(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Experience> experiencePage =
                experienceRepository.findAll(pageable);

        PageResponse<ExperienceResponse> response =
                new PageResponse<>();

        response.setContent(
                experiencePage.getContent()
                        .stream()
                        .map(ExperienceMapper::toResponse)
                        .toList());

        response.setPage(
                experiencePage.getNumber());

        response.setSize(
                experiencePage.getSize());

        response.setTotalElements(
                experiencePage.getTotalElements());

        response.setTotalPages(
                experiencePage.getTotalPages());

        response.setFirst(
                experiencePage.isFirst());

        response.setLast(
                experiencePage.isLast());

        response.setEmpty(
                experiencePage.isEmpty());

        return response;
        }
}