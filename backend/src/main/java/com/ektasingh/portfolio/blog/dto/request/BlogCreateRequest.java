package com.ektasingh.portfolio.blog.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9]+(-[a-z0-9]+)*$", message = "Slug must be lowercase letters, numbers, and hyphens only")
    private String slug;

    private String summary;

    @NotBlank(message = "Content is required")
    private String content;

    @Pattern(regexp = "^$|^(https?://).*$", message = "Cover image URL must be a valid http(s) URL")
    private String coverImageUrl;

    @NotNull(message = "Published status is required")
    private Boolean published;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be zero or positive")
    private Integer displayOrder;
}