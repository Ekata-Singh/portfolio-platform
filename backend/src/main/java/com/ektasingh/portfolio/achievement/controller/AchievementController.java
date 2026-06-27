package com.ektasingh.portfolio.achievement.controller;

import com.ektasingh.portfolio.achievement.dto.request.AchievementCreateRequest;
import com.ektasingh.portfolio.achievement.dto.response.AchievementResponse;
import com.ektasingh.portfolio.achievement.service.AchievementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping
    public ResponseEntity<AchievementResponse> createAchievement(
            @Valid @RequestBody AchievementCreateRequest request) {

        AchievementResponse response =
                achievementService.createAchievement(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementResponse> getAchievementById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                achievementService.getAchievementById(id));
    }

    @GetMapping
    public ResponseEntity<List<AchievementResponse>> getAllAchievements() {

        return ResponseEntity.ok(
                achievementService.getAllAchievements());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AchievementResponse> updateAchievement(
            @PathVariable Long id,
            @Valid @RequestBody AchievementCreateRequest request) {

        return ResponseEntity.ok(
                achievementService.updateAchievement(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(
            @PathVariable Long id) {

        achievementService.deleteAchievement(id);

        return ResponseEntity.noContent().build();
    }
}