package com.ektasingh.portfolio.project.repository;

import com.ektasingh.portfolio.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT p
        FROM Project p
        WHERE
            (:query IS NULL
            OR TRIM(:query) = ''
            OR LOWER(p.projectName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.technologies) LIKE LOWER(CONCAT('%', :query, '%')))
    """)
    Page<Project> searchProjects(
            @Param("query") String query,
            Pageable pageable);

        @Query("""
        SELECT p
        FROM Project p
        WHERE
            (:query IS NULL
            OR TRIM(:query) = ''
            OR LOWER(p.projectName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.technologies) LIKE LOWER(CONCAT('%', :query, '%')))
        ORDER BY p.displayOrder ASC
    """)
    List<Project> searchProjects(@Param("query") String query);

}