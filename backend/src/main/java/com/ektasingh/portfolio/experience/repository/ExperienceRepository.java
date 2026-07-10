package com.ektasingh.portfolio.experience.repository;

import com.ektasingh.portfolio.experience.entity.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    Page<Experience> findAll(Pageable pageable);

}