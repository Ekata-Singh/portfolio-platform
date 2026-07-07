package com.ektasingh.portfolio.achievement.service.impl;

import com.ektasingh.portfolio.achievement.dto.request.AchievementCreateRequest;
import com.ektasingh.portfolio.achievement.dto.response.AchievementResponse;
import com.ektasingh.portfolio.achievement.entity.Achievement;
import com.ektasingh.portfolio.achievement.exception.AchievementNotFoundException;
import com.ektasingh.portfolio.achievement.mapper.AchievementMapper;
import com.ektasingh.portfolio.achievement.repository.AchievementRepository;
import com.ektasingh.portfolio.achievement.service.AchievementService;
import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.storage.FileStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository repository;
    private final AchievementMapper mapper;
    private final FileStorageService fileStorageService;

    @Override
    public AchievementResponse createAchievement(AchievementCreateRequest request) {

        Achievement achievement = mapper.toEntity(request);

        Achievement savedAchievement = repository.save(achievement);

        return mapper.toResponse(savedAchievement);
    }

    @Override
    public AchievementResponse getAchievementById(Long id) {

        Achievement achievement = repository.findById(id)
                .orElseThrow(() -> new AchievementNotFoundException(id));

        return mapper.toResponse(achievement);
    }

    @Override
    public List<AchievementResponse> getAllAchievements() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PageResponse<AchievementResponse> getAchievements(
            int page,
            int size,
            String query,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Achievement> achievementPage;

        if (query != null && !query.isBlank()) {
            achievementPage = repository.searchAchievements(query, pageable);
        } else {
            achievementPage = repository.findAll(pageable);
        }

        PageResponse<AchievementResponse> response = new PageResponse<>();

        response.setContent(
                achievementPage.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );

        response.setPage(achievementPage.getNumber());
        response.setSize(achievementPage.getSize());
        response.setTotalElements(achievementPage.getTotalElements());
        response.setTotalPages(achievementPage.getTotalPages());
        response.setFirst(achievementPage.isFirst());
        response.setLast(achievementPage.isLast());
        response.setEmpty(achievementPage.isEmpty());

        return response;
    }


    @Override
    public AchievementResponse updateAchievement(
            Long id,
            AchievementCreateRequest request) {

        Achievement achievement = repository.findById(id)
                .orElseThrow(() -> new AchievementNotFoundException(id));

        mapper.updateEntity(achievement, request);

        Achievement updatedAchievement = repository.save(achievement);

        return mapper.toResponse(updatedAchievement);
    }

    @Override
    public void deleteAchievement(Long id) {

        Achievement achievement = repository.findById(id)
                .orElseThrow(() -> new AchievementNotFoundException(id));

        repository.delete(achievement);
    }

    @Override
    public AchievementResponse uploadCertificate(Long id, MultipartFile file) {

        Achievement achievement = repository.findById(id)
                .orElseThrow(() -> new AchievementNotFoundException(id));

        String certificatePath = fileStorageService.storeAchievementCertificate(file);

        achievement.setCertificateFileUrl(certificatePath);

        Achievement savedAchievement = repository.save(achievement);

        return mapper.toResponse(savedAchievement);
    }
}