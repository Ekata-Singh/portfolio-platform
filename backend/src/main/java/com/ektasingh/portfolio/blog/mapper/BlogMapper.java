package com.ektasingh.portfolio.blog.mapper;

import com.ektasingh.portfolio.blog.dto.request.BlogCreateRequest;
import com.ektasingh.portfolio.blog.dto.response.BlogResponse;
import com.ektasingh.portfolio.blog.entity.Blog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BlogMapper {

    public Blog toEntity(BlogCreateRequest request) {

        Blog blog = new Blog();

        blog.setTitle(request.getTitle());
        blog.setSlug(request.getSlug());
        blog.setSummary(request.getSummary());
        blog.setContent(request.getContent());
        blog.setCoverImageUrl(request.getCoverImageUrl());
        blog.setPublished(request.getPublished());

        if (Boolean.TRUE.equals(request.getPublished())) {
            blog.setPublishedAt(LocalDateTime.now());
        }

        blog.setDisplayOrder(request.getDisplayOrder());

        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());

        return blog;
    }

    public BlogResponse toResponse(Blog blog) {

        BlogResponse response = new BlogResponse();

        response.setId(blog.getId());
        response.setTitle(blog.getTitle());
        response.setSlug(blog.getSlug());
        response.setSummary(blog.getSummary());
        response.setContent(blog.getContent());
        response.setCoverImageUrl(blog.getCoverImageUrl());
        response.setPublished(blog.getPublished());
        response.setPublishedAt(blog.getPublishedAt());
        response.setDisplayOrder(blog.getDisplayOrder());
        response.setCreatedAt(blog.getCreatedAt());
        response.setUpdatedAt(blog.getUpdatedAt());

        return response;
    }

    public void updateEntity(Blog blog, BlogCreateRequest request) {

        blog.setTitle(request.getTitle());
        blog.setSlug(request.getSlug());
        blog.setSummary(request.getSummary());
        blog.setContent(request.getContent());
        blog.setCoverImageUrl(request.getCoverImageUrl());
        blog.setPublished(request.getPublished());

        if (Boolean.TRUE.equals(request.getPublished())
                && blog.getPublishedAt() == null) {

            blog.setPublishedAt(LocalDateTime.now());
        }

        blog.setDisplayOrder(request.getDisplayOrder());

        blog.setUpdatedAt(LocalDateTime.now());
    }
}