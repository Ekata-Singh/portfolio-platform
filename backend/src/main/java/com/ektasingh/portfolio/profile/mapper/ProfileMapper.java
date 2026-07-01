package com.ektasingh.portfolio.profile.mapper;

import com.ektasingh.portfolio.profile.dto.request.ProfileCreateRequest;
import com.ektasingh.portfolio.profile.dto.response.ProfileResponse;
import com.ektasingh.portfolio.profile.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public Profile toEntity(ProfileCreateRequest request) {

        Profile profile = new Profile();

        profile.setFullName(request.getFullName());
        profile.setHeadline(request.getHeadline());
        profile.setAbout(request.getAbout());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setLocation(request.getLocation());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setProfileImageUrl(request.getProfileImageUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setResumeUrl(request.getResumeUrl());

        return profile;
    }

    public ProfileResponse toResponse(Profile profile) {

        ProfileResponse response = new ProfileResponse();

        response.setId(profile.getId());
        response.setFullName(profile.getFullName());
        response.setHeadline(profile.getHeadline());
        response.setAbout(profile.getAbout());
        response.setEmail(profile.getEmail());
        response.setPhone(profile.getPhone());
        response.setLocation(profile.getLocation());
        response.setGithubUrl(profile.getGithubUrl());
        response.setProfileImageUrl(profile.getProfileImageUrl());
        response.setLinkedinUrl(profile.getLinkedinUrl());
        response.setResumeUrl(profile.getResumeUrl());
        response.setCreatedAt(profile.getCreatedAt());
        response.setUpdatedAt(profile.getUpdatedAt());

        return response;
    }
}