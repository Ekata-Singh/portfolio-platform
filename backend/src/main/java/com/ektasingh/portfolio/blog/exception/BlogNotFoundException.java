package com.ektasingh.portfolio.blog.exception;

public class BlogNotFoundException extends RuntimeException {

    public BlogNotFoundException(Long id) {
        super("Blog not found with id: " + id);
    }
}