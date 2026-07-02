package com.ektasingh.portfolio.blog.controller;

import com.ektasingh.portfolio.blog.dto.request.BlogCreateRequest;
import com.ektasingh.portfolio.blog.dto.response.BlogResponse;
import com.ektasingh.portfolio.blog.service.BlogService;
import com.ektasingh.portfolio.common.dto.response.PageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(
            @Valid @RequestBody BlogCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(blogService.createBlog(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(
            @PathVariable Long id) {

        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @GetMapping
    public ResponseEntity<List<BlogResponse>> getAllBlogs() {

        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long id,
            @Valid @RequestBody BlogCreateRequest request) {

        return ResponseEntity.ok(
                blogService.updateBlog(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long id) {

        blogService.deleteBlog(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponse<BlogResponse>> getBlogs(

            @RequestParam(defaultValue = "") String query,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "displayOrder") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                blogService.getBlogs(query, page, size, sortBy, direction));
    }
}