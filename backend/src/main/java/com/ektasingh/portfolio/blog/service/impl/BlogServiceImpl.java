package com.ektasingh.portfolio.blog.service.impl;

import com.ektasingh.portfolio.blog.dto.request.BlogCreateRequest;
import com.ektasingh.portfolio.blog.dto.response.BlogResponse;
import com.ektasingh.portfolio.blog.entity.Blog;
import com.ektasingh.portfolio.blog.exception.BlogNotFoundException;
import com.ektasingh.portfolio.blog.mapper.BlogMapper;
import com.ektasingh.portfolio.blog.repository.BlogRepository;
import com.ektasingh.portfolio.blog.service.BlogService;
import com.ektasingh.portfolio.common.dto.response.PageResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository repository;
    private final BlogMapper mapper;

    @Override
    public BlogResponse createBlog(BlogCreateRequest request) {

        Blog blog = mapper.toEntity(request);

        Blog savedBlog = repository.save(blog);

        return mapper.toResponse(savedBlog);
    }

    @Override
    public BlogResponse getBlogById(Long id) {

        Blog blog = repository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException(id));

        return mapper.toResponse(blog);
    }

    @Override
    public List<BlogResponse> getAllBlogs() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public BlogResponse updateBlog(Long id,
                                   BlogCreateRequest request) {

        Blog blog = repository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException(id));

        mapper.updateEntity(blog, request);

        Blog updatedBlog = repository.save(blog);

        return mapper.toResponse(updatedBlog);
    }

    @Override
    public void deleteBlog(Long id) {

        Blog blog = repository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException(id));

        repository.delete(blog);
    }

    @Override
    public PageResponse<BlogResponse> getBlogs(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Blog> blogPage =
                repository.findAllByOrderByDisplayOrderAsc(pageable);

        PageResponse<BlogResponse> response = new PageResponse<>();

        response.setContent(
                blogPage.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );

        response.setPage(blogPage.getNumber());

        response.setSize(blogPage.getSize());

        response.setTotalElements(blogPage.getTotalElements());

        response.setTotalPages(blogPage.getTotalPages());

        response.setFirst(blogPage.isFirst());

        response.setLast(blogPage.isLast());

        response.setEmpty(blogPage.isEmpty());

        return response;
    }
}