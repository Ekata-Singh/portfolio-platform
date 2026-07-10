package com.ektasingh.portfolio.skill.repository;

import com.ektasingh.portfolio.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}