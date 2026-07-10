package com.ektasingh.portfolio.certificate.mapper;

import com.ektasingh.portfolio.certificate.dto.request.CertificationCreateRequest;
import com.ektasingh.portfolio.certificate.dto.response.CertificationResponse;
import com.ektasingh.portfolio.certificate.entity.Certification;
import org.springframework.stereotype.Component;

@Component
public class CertificationMapper {

    public Certification toEntity(CertificationCreateRequest request) {

        Certification certification = new Certification();

        certification.setCertificationName(request.getCertificationName());
        certification.setIssuingOrganization(request.getIssuingOrganization());
        certification.setIssueDate(request.getIssueDate());
        certification.setExpiryDate(request.getExpiryDate());
        certification.setCredentialId(request.getCredentialId());
        certification.setCredentialUrl(request.getCredentialUrl());
        certification.setType(request.getType());
        certification.setCategory(request.getCategory());
        certification.setThumbnailUrl(request.getThumbnailUrl());
        certification.setDisplayOrder(request.getDisplayOrder());

        return certification;
    }

    public CertificationResponse toResponse(Certification certification) {

        CertificationResponse response = new CertificationResponse();

        response.setId(certification.getId());
        response.setCertificationName(certification.getCertificationName());
        response.setIssuingOrganization(certification.getIssuingOrganization());
        response.setIssueDate(certification.getIssueDate());
        response.setExpiryDate(certification.getExpiryDate());
        response.setCredentialId(certification.getCredentialId());
        response.setCredentialUrl(certification.getCredentialUrl());
        response.setType(certification.getType());
        response.setCategory(certification.getCategory());
        response.setThumbnailUrl(certification.getThumbnailUrl());
        response.setDisplayOrder(certification.getDisplayOrder());
        response.setCreatedAt(certification.getCreatedAt());
        response.setUpdatedAt(certification.getUpdatedAt());

        return response;
    }

    public void updateEntity(
            Certification certification,
            CertificationCreateRequest request) {

        certification.setCertificationName(request.getCertificationName());
        certification.setIssuingOrganization(request.getIssuingOrganization());
        certification.setIssueDate(request.getIssueDate());
        certification.setExpiryDate(request.getExpiryDate());
        certification.setCredentialId(request.getCredentialId());
        certification.setCredentialUrl(request.getCredentialUrl());
        certification.setType(request.getType());
        certification.setCategory(request.getCategory());
        certification.setThumbnailUrl(request.getThumbnailUrl());
        certification.setDisplayOrder(request.getDisplayOrder());
    }
}