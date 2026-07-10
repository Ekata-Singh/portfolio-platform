package com.ektasingh.portfolio.education.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationCreateRequest {

    @NotBlank(message = "Institution is required")
    private String institution;

    @NotBlank(message = "Degree is required")
    private String degree;

    @NotBlank(message = "Field of study is required")
    private String fieldOfStudy;

    @NotNull(message = "Start year is required")
    @Min(value = 1950, message = "Start year is invalid")
    @Max(value = 2100, message = "Start year is invalid")
    private Integer startYear;

    @Min(value = 1950, message = "End year is invalid")
    @Max(value = 2100, message = "End year is invalid")
    private Integer endYear;

    private String grade;

    private String description;

    private String subjects;
}