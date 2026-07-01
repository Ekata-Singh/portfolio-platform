package com.ektasingh.portfolio.skill.repository;

import com.ektasingh.portfolio.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
// @Repository


public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("""
       SELECT s
       FROM Skill s
       WHERE
       LOWER(s.skillName) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(s.category) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(s.proficiency) LIKE LOWER(CONCAT('%', :query, '%'))
       ORDER BY s.displayOrder
       """)
    List<Skill> searchSkills(@Param("query") String query);

    Page<Skill> findAllByOrderByDisplayOrderAsc(Pageable pageable);
}