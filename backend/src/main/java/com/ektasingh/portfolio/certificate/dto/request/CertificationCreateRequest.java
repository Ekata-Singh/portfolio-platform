package com.ektasingh.portfolio.certificate.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CertificationCreateRequest {

    @NotBlank(message = "Certification name is required")
    private String certificationName;

    @NotBlank(message = "Issuing organization is required")
    private String issuingOrganization;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    private String credentialId;

    @Pattern(regexp = "^$|^(https?://).*$", message = "Credential URL must be a valid http(s) URL")
    private String credentialUrl;

    private String type;

    private String category;

    @Pattern(
            regexp = "^$|^(https?://).*$|^certificate/.*$",
            message = "Thumbnail URL must be a valid http(s) URL")
    private String thumbnailUrl;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be zero or positive")
    private Integer displayOrder;
}