package com.ektasingh.portfolio.profile.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileResponse {

    private Long id;

    private String fullName;

    private String headline;

    private String about;

    private String email;

    private String phone;

    private String location;

    private String githubUrl;

    private String profileImageUrl;

    private String linkedinUrl;

    private String resumeUrl;

    private String codeforcesUrl;

    private String leetcodeUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}