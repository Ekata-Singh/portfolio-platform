package com.ektasingh.portfolio.technology.repository;

import com.ektasingh.portfolio.technology.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

}