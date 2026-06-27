package com.ektasingh.portfolio.technology.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TechnologyResponse {

    private Long id;

    private String technologyName;

    private String category;

    private String iconUrl;

    private String proficiency;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}