package com.ektasingh.portfolio.achievement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private String achievementUrl;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}