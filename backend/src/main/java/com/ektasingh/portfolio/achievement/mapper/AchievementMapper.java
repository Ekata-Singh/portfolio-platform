package com.ektasingh.portfolio.achievement.mapper;

import com.ektasingh.portfolio.achievement.dto.request.AchievementCreateRequest;
import com.ektasingh.portfolio.achievement.dto.response.AchievementResponse;
import com.ektasingh.portfolio.achievement.entity.Achievement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AchievementMapper {

    public Achievement toEntity(AchievementCreateRequest request) {

        Achievement achievement = new Achievement();

        achievement.setTitle(request.getTitle());
        achievement.setOrganization(request.getOrganization());
        achievement.setAchievementDate(request.getAchievementDate());
        achievement.setDescription(request.getDescription());
        achievement.setAchievementUrl(request.getAchievementUrl());
        achievement.setDisplayOrder(request.getDisplayOrder());

        achievement.setCreatedAt(LocalDateTime.now());
        achievement.setUpdatedAt(LocalDateTime.now());

        return achievement;
    }

    public AchievementResponse toResponse(Achievement achievement) {

        AchievementResponse response = new AchievementResponse();

        response.setId(achievement.getId());
        response.setTitle(achievement.getTitle());
        response.setOrganization(achievement.getOrganization());
        response.setAchievementDate(achievement.getAchievementDate());
        response.setDescription(achievement.getDescription());
        response.setAchievementUrl(achievement.getAchievementUrl());
        response.setDisplayOrder(achievement.getDisplayOrder());
        response.setCreatedAt(achievement.getCreatedAt());
        response.setUpdatedAt(achievement.getUpdatedAt());

        return response;
    }

    public void updateEntity(
            Achievement achievement,
            AchievementCreateRequest request) {

        achievement.setTitle(request.getTitle());
        achievement.setOrganization(request.getOrganization());
        achievement.setAchievementDate(request.getAchievementDate());
        achievement.setDescription(request.getDescription());
        achievement.setAchievementUrl(request.getAchievementUrl());
        achievement.setDisplayOrder(request.getDisplayOrder());

        achievement.setUpdatedAt(LocalDateTime.now());
    }
}