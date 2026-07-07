package com.ektasingh.portfolio.achievement.controller;

import com.ektasingh.portfolio.achievement.dto.request.AchievementCreateRequest;
import com.ektasingh.portfolio.achievement.dto.response.AchievementResponse;
import com.ektasingh.portfolio.achievement.service.AchievementService;
import com.ektasingh.portfolio.common.dto.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/achievement")
@RequiredArgsConstructor
@Tag(name = "Achievements", description = "Achievement Management APIs")
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping
    @Operation(summary = "Create Achievement")
    public ResponseEntity<AchievementResponse> createAchievement(
            @Valid @RequestBody AchievementCreateRequest request) {

        AchievementResponse response =
                achievementService.createAchievement(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Achievement by ID")
    public ResponseEntity<AchievementResponse> getAchievementById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                achievementService.getAchievementById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Achievements")
    public ResponseEntity<List<AchievementResponse>> getAllAchievements() {

        return ResponseEntity.ok(
                achievementService.getAllAchievements());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Achievement")
    public ResponseEntity<AchievementResponse> updateAchievement(
            @PathVariable Long id,
            @Valid @RequestBody AchievementCreateRequest request) {

        return ResponseEntity.ok(
                achievementService.updateAchievement(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Achievement")
    public ResponseEntity<Void> deleteAchievement(
            @PathVariable Long id) {

        achievementService.deleteAchievement(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(
        value = "/{id}/certificate",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Upload Achievement Certificate")
    public ResponseEntity<AchievementResponse> uploadCertificate(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                achievementService.uploadCertificate(id, file));
    }

    @GetMapping("/page")
        @Operation(summary = "Get Paginated Achievements")
        public ResponseEntity<PageResponse<AchievementResponse>> getAchievements(

                @RequestParam(defaultValue = "0") int page,

                @RequestParam(defaultValue = "10") int size,

                @RequestParam(required = false) String query,

                @RequestParam(defaultValue = "displayOrder") String sortBy,

                @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                achievementService.getAchievements(
                        page,
                        size,
                        query,
                        sortBy,
                        direction));
        }
}