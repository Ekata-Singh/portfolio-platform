package com.ektasingh.portfolio.profile.service;

import java.util.List;
import com.ektasingh.portfolio.profile.dto.request.ProfileCreateRequest;
import com.ektasingh.portfolio.profile.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileCreateRequest request);

    ProfileResponse getProfileById(Long id);

    List<ProfileResponse> getAllProfiles();

    ProfileResponse updateProfile(Long id, ProfileCreateRequest request);

    void deleteProfile(Long id);

}