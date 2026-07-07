package com.ektasingh.portfolio.achievement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AchievementCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String organization;

    private LocalDate achievementDate;

    private String description;

    @Pattern(regexp = "^$|^(https?://).*$", message = "Achievement URL must be a valid http(s) URL")
    private String achievementUrl;

    private String category;

    private String status;

    @Pattern(
            regexp = "^$|^(https?://).*$|^achievement/.*$",
            message = "Certificate file URL must be a valid http(s) URL")
    private String certificateFileUrl;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be zero or positive")
    private Integer displayOrder;
}