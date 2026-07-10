package com.ektasingh.portfolio.blog.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BlogResponse {

    private Long id;

    private String title;

    private String slug;

    private String summary;

    private String content;

    private String coverImageUrl;

    private Boolean published;

    private LocalDateTime publishedAt;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}