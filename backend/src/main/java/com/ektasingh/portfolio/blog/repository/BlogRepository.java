package com.ektasingh.portfolio.blog.repository;

import com.ektasingh.portfolio.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
// @Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("""
       SELECT b
       FROM Blog b
       WHERE
       LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(b.summary) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(b.content) LIKE LOWER(CONCAT('%', :query, '%'))
       ORDER BY b.displayOrder
       """)
    List<Blog> searchBlogs(@Param("query") String query);

    Page<Blog> findAllByOrderByDisplayOrderAsc(Pageable pageable);
}