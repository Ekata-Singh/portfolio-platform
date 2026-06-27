package com.ektasingh.portfolio.publication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private String publicationUrl;

    private String description;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}