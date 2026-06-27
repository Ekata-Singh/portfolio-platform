package com.ektasingh.portfolio.experience.repository;

import com.ektasingh.portfolio.experience.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}