package com.ektasingh.portfolio.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Slug is required")
    private String slug;

    private String summary;

    @NotBlank(message = "Content is required")
    private String content;

    private String coverImageUrl;

    @NotNull(message = "Published status is required")
    private Boolean published;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}