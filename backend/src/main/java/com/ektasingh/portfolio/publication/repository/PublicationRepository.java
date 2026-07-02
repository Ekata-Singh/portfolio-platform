package com.ektasingh.portfolio.publication.repository;

import com.ektasingh.portfolio.publication.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

    Page<Publication> findAllByOrderByDisplayOrderAsc(Pageable pageable);

    @Query("""
        SELECT p
        FROM Publication p
        WHERE
            LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.publisher) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))
        """)
    Page<Publication> searchPublications(
            @Param("query") String query,
            Pageable pageable
    );

    @Query("""
        SELECT p
        FROM Publication p
        WHERE
            LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.publisher) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY p.displayOrder
        """)
    List<Publication> searchPublications(
            @Param("query") String query
    );
}