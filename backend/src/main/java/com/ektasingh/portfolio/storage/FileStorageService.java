package com.ektasingh.portfolio.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeProfileImage(MultipartFile file);

    String storeResume(MultipartFile file);

    String storeProjectThumbnail(MultipartFile file);

    String storeAchievementCertificate(MultipartFile file);

    String storePublicationThumbnail(MultipartFile file);

    String storeCertificationThumbnail(MultipartFile file);

}