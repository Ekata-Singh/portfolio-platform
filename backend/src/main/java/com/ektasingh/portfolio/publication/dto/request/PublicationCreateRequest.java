package com.ektasingh.portfolio.publication.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PublicationCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Publisher is required")
    private String publisher;

    @NotNull(message = "Publication date is required")
    private LocalDate publicationDate;

    @Pattern(regexp = "^$|^(https?://).*$", message = "Publication URL must be a valid http(s) URL")
    private String publicationUrl;

    private String description;

    private String tags;

    @Pattern(
            regexp = "^$|^(https?://).*$|^publication/.*$",
            message = "Thumbnail URL must be a valid http(s) URL")
    private String thumbnailUrl;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be zero or positive")
    private Integer displayOrder;
}