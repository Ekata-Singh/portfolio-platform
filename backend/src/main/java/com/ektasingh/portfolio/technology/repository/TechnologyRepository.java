package com.ektasingh.portfolio.technology.repository;

import com.ektasingh.portfolio.technology.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
// @Repository


public interface TechnologyRepository extends JpaRepository<Technology, Long> {

    @Query("""
        SELECT t
        FROM Technology t
        WHERE
        LOWER(t.technologyName) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(t.category) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(t.proficiency) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY t.displayOrder
        """)
    List<Technology> searchTechnologies(@Param("query") String query);

    Page<Technology> findAllByOrderByDisplayOrderAsc(Pageable pageable);
}