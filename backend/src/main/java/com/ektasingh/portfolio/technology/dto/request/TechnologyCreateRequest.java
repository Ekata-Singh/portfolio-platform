package com.ektasingh.portfolio.technology.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnologyCreateRequest {

    @NotBlank(message = "Technology name is required")
    private String technologyName;

    @NotBlank(message = "Category is required")
    private String category;

    @Pattern(regexp = "^$|^(https?://).*$", message = "Icon URL must be a valid http(s) URL")
    private String iconUrl;

    private String proficiency;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be zero or positive")
    private Integer displayOrder;
}