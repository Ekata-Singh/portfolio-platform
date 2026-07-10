package com.ektasingh.portfolio.certificate.service;

import com.ektasingh.portfolio.certificate.dto.request.CertificationCreateRequest;
import com.ektasingh.portfolio.certificate.dto.response.CertificationResponse;

import java.util.List;

public interface CertificationService {

    CertificationResponse createCertification(CertificationCreateRequest request);

    CertificationResponse getCertificationById(Long id);

    List<CertificationResponse> getAllCertifications();

    CertificationResponse updateCertification(
            Long id,
            CertificationCreateRequest request);

    void deleteCertification(Long id);
}