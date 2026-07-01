package com.ektasingh.portfolio.certificate.controller;

import com.ektasingh.portfolio.certificate.dto.request.CertificationCreateRequest;
import com.ektasingh.portfolio.certificate.dto.response.CertificationResponse;
import com.ektasingh.portfolio.certificate.service.CertificationService;
import com.ektasingh.portfolio.common.dto.response.PageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificate")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping
    public ResponseEntity<CertificationResponse> createCertification(
            @Valid @RequestBody CertificationCreateRequest request) {

        CertificationResponse response =
                certificationService.createCertification(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponse> getCertificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                certificationService.getCertificationById(id));
    }

    @GetMapping
    public ResponseEntity<List<CertificationResponse>> getAllCertifications() {

        return ResponseEntity.ok(
                certificationService.getAllCertifications());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationResponse> updateCertification(
            @PathVariable Long id,
            @Valid @RequestBody CertificationCreateRequest request) {

        return ResponseEntity.ok(
                certificationService.updateCertification(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(
            @PathVariable Long id) {

        certificationService.deleteCertification(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponse<CertificationResponse>> getCertifications(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                certificationService.getCertifications(page, size));
    }
}