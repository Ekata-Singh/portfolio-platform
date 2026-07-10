package com.ektasingh.portfolio.certificate.repository;

import com.ektasingh.portfolio.certificate.entity.Certification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    @Query("""
        SELECT c
        FROM Certification c
        WHERE LOWER(c.certificationName)
                LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(c.issuingOrganization)
                LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(c.credentialId)
                LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    Page<Certification> searchCertifications(
            @Param("query") String query,
            Pageable pageable
    );
}