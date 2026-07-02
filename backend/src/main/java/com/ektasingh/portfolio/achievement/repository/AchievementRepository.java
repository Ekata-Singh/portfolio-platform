package com.ektasingh.portfolio.achievement.repository;

import com.ektasingh.portfolio.achievement.entity.Achievement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Page<Achievement> findAllByOrderByDisplayOrderAsc(Pageable pageable);

    @Query("""
            SELECT a
            FROM Achievement a
            WHERE
                LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.organization) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.description) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    Page<Achievement> searchAchievements(
            @Param("query") String query,
            Pageable pageable
    );

    @Query("""
            SELECT a
            FROM Achievement a
            WHERE
                LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.organization) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.description) LIKE LOWER(CONCAT('%', :query, '%'))
            ORDER BY a.displayOrder
            """)
    List<Achievement> searchAchievements(
            @Param("query") String query
    );
}