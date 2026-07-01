package com.ektasingh.portfolio.contact.repository;

import com.ektasingh.portfolio.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Repository;

// @Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findAllByOrderByIdAsc(Pageable pageable);

}