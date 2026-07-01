package com.ektasingh.portfolio.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeProfileImage(MultipartFile file);

    String storeResume(MultipartFile file);

}