package com.ektasingh.portfolio.certificate.controller;

import com.ektasingh.portfolio.certificate.dto.request.CertificationCreateRequest;
import com.ektasingh.portfolio.certificate.dto.response.CertificationResponse;
import com.ektasingh.portfolio.certificate.service.CertificationService;
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
@RequestMapping("/api/v1/certificate")
@RequiredArgsConstructor
@Tag(name = "Certificates", description = "Certification Management APIs")
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping
    @Operation(summary = "Create Certification")
    public ResponseEntity<CertificationResponse> createCertification(
            @Valid @RequestBody CertificationCreateRequest request) {

        CertificationResponse response =
                certificationService.createCertification(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Certification by ID")
    public ResponseEntity<CertificationResponse> getCertificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                certificationService.getCertificationById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Certifications")
    public ResponseEntity<List<CertificationResponse>> getAllCertifications() {

        return ResponseEntity.ok(
                certificationService.getAllCertifications());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Certification")
    public ResponseEntity<CertificationResponse> updateCertification(
            @PathVariable Long id,
            @Valid @RequestBody CertificationCreateRequest request) {

        return ResponseEntity.ok(
                certificationService.updateCertification(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Certification")
    public ResponseEntity<Void> deleteCertification(
            @PathVariable Long id) {

        certificationService.deleteCertification(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(
        value = "/{id}/thumbnail",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Upload Certification Thumbnail")
    public ResponseEntity<CertificationResponse> uploadThumbnail(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                certificationService.uploadThumbnail(id, file));
    }

    @GetMapping("/page")
        @Operation(summary = "Get Paginated Certifications")
        public ResponseEntity<PageResponse<CertificationResponse>> getCertifications(

                @RequestParam(defaultValue = "0") int page,

                @RequestParam(defaultValue = "10") int size,

                @RequestParam(required = false) String query,

                @RequestParam(defaultValue = "displayOrder") String sortBy,

                @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                certificationService.getCertifications(
                        page,
                        size,
                        query,
                        sortBy,
                        direction
                ));
        }
}