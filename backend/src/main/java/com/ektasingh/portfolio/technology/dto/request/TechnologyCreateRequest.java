package com.ektasingh.portfolio.technology.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnologyCreateRequest {

    @NotBlank(message = "Technology name is required")
    private String technologyName;

    @NotBlank(message = "Category is required")
    private String category;

    private String iconUrl;

    private String proficiency;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}