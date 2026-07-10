package com.ektasingh.portfolio.contact.repository;

import com.ektasingh.portfolio.contact.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("""
        SELECT c
        FROM Contact c
        WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(c.city) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(c.country) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    Page<Contact> searchContacts(
            @Param("query") String query,
            Pageable pageable
    );
}