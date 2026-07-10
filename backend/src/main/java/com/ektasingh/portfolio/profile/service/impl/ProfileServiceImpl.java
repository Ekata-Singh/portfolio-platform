package com.ektasingh.portfolio.profile.service.impl;

import com.ektasingh.portfolio.profile.entity.Profile;
import com.ektasingh.portfolio.profile.exception.ProfileNotFoundException;
import com.ektasingh.portfolio.profile.dto.request.ProfileCreateRequest;
import com.ektasingh.portfolio.profile.dto.response.ProfileResponse;
import com.ektasingh.portfolio.profile.mapper.ProfileMapper;
import com.ektasingh.portfolio.profile.repository.ProfileRepository;
import com.ektasingh.portfolio.profile.service.ProfileService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;
    private final ProfileMapper mapper;

    public ProfileServiceImpl(ProfileRepository repository,
                              ProfileMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProfileResponse createProfile(ProfileCreateRequest request) {

        Profile profile = mapper.toEntity(request);

        Profile savedProfile = repository.save(profile);

        return mapper.toResponse(savedProfile);

    }

    @Override
    public ProfileResponse getProfileById(Long id) {

        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        return mapper.toResponse(profile);
    }

    @Override
    public List<ProfileResponse> getAllProfiles() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ProfileResponse updateProfile(Long id,
                                        ProfileCreateRequest request) {

        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        profile.setFullName(request.getFullName());
        profile.setHeadline(request.getHeadline());
        profile.setAbout(request.getAbout());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setLocation(request.getLocation());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setResumeUrl(request.getResumeUrl());

        Profile updatedProfile = repository.save(profile);

        return mapper.toResponse(updatedProfile);
    }

    @Override
    public void deleteProfile(Long id) {

        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        repository.delete(profile);
    }
}
