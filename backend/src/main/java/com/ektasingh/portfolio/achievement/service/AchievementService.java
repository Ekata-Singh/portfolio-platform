package com.ektasingh.portfolio.achievement.service;

import com.ektasingh.portfolio.achievement.dto.request.AchievementCreateRequest;
import com.ektasingh.portfolio.achievement.dto.response.AchievementResponse;
import com.ektasingh.portfolio.common.dto.response.PageResponse;

import java.util.List;

public interface AchievementService {

    AchievementResponse createAchievement(AchievementCreateRequest request);

    AchievementResponse getAchievementById(Long id);

    List<AchievementResponse> getAllAchievements();

    PageResponse<AchievementResponse> getAchievements(
        int page,
        int size,
        String query,
        String sortBy,
        String direction
);

    AchievementResponse updateAchievement(
            Long id,
            AchievementCreateRequest request);

    void deleteAchievement(Long id);
}