package com.ektasingh.portfolio.blog.service;

import com.ektasingh.portfolio.blog.dto.request.BlogCreateRequest;
import com.ektasingh.portfolio.blog.dto.response.BlogResponse;
import com.ektasingh.portfolio.common.dto.response.PageResponse;
import java.util.List;

public interface BlogService {

    BlogResponse createBlog(BlogCreateRequest request);

    BlogResponse getBlogById(Long id);

    List<BlogResponse> getAllBlogs();

    PageResponse<BlogResponse> getBlogs(int page, int size);

    BlogResponse updateBlog(Long id,
                            BlogCreateRequest request);

    void deleteBlog(Long id);
}