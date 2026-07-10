package com.ektasingh.portfolio.profile.controller;

import com.ektasingh.portfolio.profile.service.ProfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ektasingh.portfolio.profile.dto.request.ProfileCreateRequest;
import com.ektasingh.portfolio.profile.dto.response.ProfileResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile", description = "Profile Management APIs")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Profile")
    public ProfileResponse createProfile(
            @Valid @RequestBody ProfileCreateRequest request) {

        return profileService.createProfile(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Profile by ID")
    public ProfileResponse getProfile(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    @GetMapping
    @Operation(summary = "Get All Profiles")
    public List<ProfileResponse> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Profile")
    public ProfileResponse updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody ProfileCreateRequest request) {

        return profileService.updateProfile(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Profile")
    public void deleteProfile(@PathVariable Long id) {

        profileService.deleteProfile(id);
    }

    @PostMapping(
        value = "/{id}/image",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Upload Profile Image")
    public ResponseEntity<ProfileResponse> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {

        return ResponseEntity.ok(
                profileService.uploadProfileImage(id, file)
        );
    }

    @PostMapping(
        value = "/{id}/resume",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Upload Resume")
    public ResponseEntity<ProfileResponse> uploadResume(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {

        return ResponseEntity.ok(
                profileService.uploadResume(id, file)
        );
    }

}

