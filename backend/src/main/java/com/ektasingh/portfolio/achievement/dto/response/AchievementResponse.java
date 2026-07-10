package com.ektasingh.portfolio.achievement.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AchievementResponse {

    private Long id;

    private String title;

    private String organization;

    private LocalDate achievementDate;

    private String description;

    private String achievementUrl;

    private String category;

    private String status;

    private String certificateFileUrl;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}