package com.ektasingh.portfolio.profile.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProfileCreateRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Headline is required")
    @Size(max = 255)
    private String headline;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @Size(max = 2000)
    private String about;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String location;

    @Size(max = 255)
    @Pattern(regexp = "^$|^(https?://).*$", message = "GitHub URL must be a valid http(s) URL")
    private String githubUrl;

    @Size(max = 500)
    @Pattern(
            regexp = "^$|^(https?://).*$|^profile/.*$",
            message = "Profile image URL must be a valid http(s) URL")
    private String profileImageUrl;

    @Size(max = 255)
    @Pattern(regexp = "^$|^(https?://).*$", message = "LinkedIn URL must be a valid http(s) URL")
    private String linkedinUrl;

    @Size(max = 255)
    @Pattern(
            regexp = "^$|^(https?://).*$|^resume/.*$",
            message = "Resume URL must be a valid http(s) URL")
    private String resumeUrl;

    @Size(max = 255)
    @Pattern(
            regexp = "^$|^(https?://).*$",
            message = "Codeforces URL must be a valid http(s) URL")
    private String codeforcesUrl;

    @Size(max = 255)
    @Pattern(
            regexp = "^$|^(https?://).*$",
            message = "LeetCode URL must be a valid http(s) URL")
    private String leetcodeUrl;
}

