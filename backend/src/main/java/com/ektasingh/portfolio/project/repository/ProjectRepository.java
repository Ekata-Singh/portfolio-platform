package com.ektasingh.portfolio.project.repository;

import com.ektasingh.portfolio.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT p
        FROM Project p
        WHERE
            LOWER(p.projectName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.technologies) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY p.displayOrder
    """)
    java.util.List<Project> searchProjects(@Param("query") String query);

    Page<Project> findAllByOrderByDisplayOrderAsc(Pageable pageable);

}