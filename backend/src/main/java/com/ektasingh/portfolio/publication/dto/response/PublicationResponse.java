package com.ektasingh.portfolio.publication.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PublicationResponse {

    private Long id;

    private String title;

    private String publisher;

    private LocalDate publicationDate;

    private String publicationUrl;

    private String description;

    private String tags;

    private String thumbnailUrl;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}