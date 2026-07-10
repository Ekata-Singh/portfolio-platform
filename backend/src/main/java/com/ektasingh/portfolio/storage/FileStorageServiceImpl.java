package com.ektasingh.portfolio.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String storeProfileImage(MultipartFile file) {
        return storeFile(file, "profile");
    }

    @Override
    public String storeResume(MultipartFile file) {
        return storeFile(file, "resume");
    }

    @Override
    public String storeProjectThumbnail(MultipartFile file) {
        return storeFile(file, "project");
    }

    @Override
    public String storeAchievementCertificate(MultipartFile file) {
        return storeFile(file, "achievement");
    }

    @Override
    public String storePublicationThumbnail(MultipartFile file) {
        return storeFile(file, "publication");
    }

    @Override
    public String storeCertificationThumbnail(MultipartFile file) {
        return storeFile(file, "certificate");
    }

    /**
     * Common method for saving any uploaded file.
     */
    private String storeFile(MultipartFile file, String folderName) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File cannot be empty.");
        }

        try {

            // Create uploads/profile or uploads/resume
            Path folderPath = Paths.get(uploadDir, folderName);
            Files.createDirectories(folderPath);

            // Original filename
            String originalFilename =
                    StringUtils.cleanPath(file.getOriginalFilename());

            // Generate unique filename
            String fileName =
                    System.currentTimeMillis() + "_" + originalFilename;

            // Destination
            Path targetLocation = folderPath.resolve(fileName);

            // Copy file
            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Return relative path to save in DB
            return folderName + "/" + fileName;

        } catch (IOException ex) {

            throw new RuntimeException(
                    "Could not store file: " + ex.getMessage()
            );

        }
    }
}