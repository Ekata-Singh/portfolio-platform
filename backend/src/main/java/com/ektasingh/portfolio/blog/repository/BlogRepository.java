package com.ektasingh.portfolio.blog.repository;

import com.ektasingh.portfolio.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}