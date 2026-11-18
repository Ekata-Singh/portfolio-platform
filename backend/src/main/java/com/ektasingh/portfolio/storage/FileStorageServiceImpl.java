package com.ektasingh.portfolio.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;

/**
 * Stores uploaded files.
 *
 * - When Cloudinary credentials are set (production), files go to Cloudinary and the
 *   full https URL is stored in the database.
 * - Otherwise (local development), files are saved under {@code app.storage.local-dir}
 *   and served by the API at /uploads/**. The stored value is a relative path such as
 *   "profile/1712345678_photo.jpg", which the frontend turns into a full URL.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private final Cloudinary cloudinary;

    private final Path localRoot;

    public FileStorageServiceImpl(
            Cloudinary cloudinary,
            @Value("${app.storage.local-dir}") String localDir) {
        this.cloudinary = cloudinary;
        this.localRoot = Path.of(localDir).toAbsolutePath().normalize();

        if (!isCloudinaryConfigured()) {
            log.info("Cloudinary is not configured - uploaded files will be stored locally in {}", localRoot);
        }
    }

    @Override
    public String storeProfileImage(MultipartFile file) {
        return uploadFile(file, "profile");
    }

    @Override
    public String storeResume(MultipartFile file) {
        return uploadFile(file, "resume");
    }

    @Override
    public String storeProjectThumbnail(MultipartFile file) {
        return uploadFile(file, "project");
    }

    @Override
    public String storeAchievementCertificate(MultipartFile file) {
        return uploadFile(file, "achievement");
    }

    @Override
    public String storePublicationThumbnail(MultipartFile file) {
        return uploadFile(file, "publication");
    }

    @Override
    public String storeCertificationThumbnail(MultipartFile file) {
        return uploadFile(file, "certificate");
    }

    private String uploadFile(MultipartFile file, String folderName) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !isAllowedType(contentType)) {
            throw new IllegalArgumentException(
                    "Unsupported file type. Upload an image (JPG, PNG, WebP, GIF, SVG) or a PDF.");
        }

        return isCloudinaryConfigured()
                ? uploadToCloudinary(file, folderName)
                : saveLocally(file, folderName);
    }

    private String uploadToCloudinary(MultipartFile file, String folderName) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "portfolio/" + folderName,
                            "resource_type", "auto"
                    )
            );

            return (String) uploadResult.get("secure_url");

        } catch (IOException | RuntimeException ex) {
            throw new FileStorageException("Could not upload file: " + ex.getMessage(), ex);
        }
    }

    private String saveLocally(MultipartFile file, String folderName) {
        String original = StringUtils.cleanPath(
                file.getOriginalFilename() == null ? "file" : file.getOriginalFilename());
        String safeName = original.replaceAll("[^A-Za-z0-9._-]", "_");
        String fileName = System.currentTimeMillis() + "_" + safeName;

        Path folder = localRoot.resolve(folderName).normalize();
        Path target = folder.resolve(fileName).normalize();

        if (!target.startsWith(localRoot)) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        try (InputStream in = file.getInputStream()) {
            Files.createDirectories(folder);
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not save file: " + ex.getMessage(), ex);
        }

        return folderName + "/" + fileName;
    }

    private boolean isCloudinaryConfigured() {
        return StringUtils.hasText(cloudinary.config.cloudName)
                && StringUtils.hasText(cloudinary.config.apiKey)
                && StringUtils.hasText(cloudinary.config.apiSecret);
    }

    private static boolean isAllowedType(String contentType) {
        String type = contentType.toLowerCase();
        return type.startsWith("image/") || type.equals("application/pdf");
    }
}
