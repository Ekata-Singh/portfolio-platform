package com.ektasingh.portfolio.profile.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ektasingh.portfolio.profile.dto.request.ProfileCreateRequest;
import com.ektasingh.portfolio.profile.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileCreateRequest request);

    ProfileResponse getProfileById(Long id);

    List<ProfileResponse> getAllProfiles();

    ProfileResponse updateProfile(Long id, ProfileCreateRequest request);

    void deleteProfile(Long id);

    ProfileResponse uploadProfileImage(Long id, MultipartFile file);

    ProfileResponse uploadResume(Long id, MultipartFile file);

}