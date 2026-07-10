package com.ektasingh.portfolio.education.repository;

import com.ektasingh.portfolio.education.entity.Education;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository
        extends JpaRepository<Education, Long> {

    Page<Education> findAll(Pageable pageable);
}