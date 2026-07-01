package com.ektasingh.portfolio.certificate.repository;

import com.ektasingh.portfolio.certificate.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Repository;

// @Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    Page<Certification> findAllByOrderByDisplayOrderAsc(Pageable pageable);

}