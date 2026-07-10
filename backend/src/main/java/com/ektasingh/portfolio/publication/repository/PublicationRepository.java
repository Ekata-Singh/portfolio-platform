package com.ektasingh.portfolio.publication.repository;

import com.ektasingh.portfolio.publication.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}