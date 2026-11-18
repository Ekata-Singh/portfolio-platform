package com.ektasingh.portfolio.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Cloudinary cloudinary;

    public FileStorageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
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

    /**
     * Uploads to Cloudinary and returns the full secure_url, which is stored
     * as-is in the database (resolveAssetUrl on the frontend passes http(s)
     * URLs through unchanged).
     */
    private String uploadFile(MultipartFile file, String folderName) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !isAllowedType(contentType)) {
            throw new IllegalArgumentException(
                    "Unsupported file type. Upload an image (JPG, PNG, WebP, GIF, SVG) or a PDF.");
        }

        if (!StringUtils.hasText(cloudinary.config.cloudName)
                || !StringUtils.hasText(cloudinary.config.apiKey)
                || !StringUtils.hasText(cloudinary.config.apiSecret)) {
            throw new FileStorageException(
                    "File uploads are not configured. Set CLOUDINARY_CLOUD_NAME, CLOUDINARY_API_KEY and CLOUDINARY_API_SECRET.");
        }

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

    private static boolean isAllowedType(String contentType) {
        String type = contentType.toLowerCase();
        return type.startsWith("image/") || type.equals("application/pdf");
    }
}
