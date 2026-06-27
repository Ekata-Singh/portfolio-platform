package com.ektasingh.portfolio.certificate.exception;

public class CertificationNotFoundException extends RuntimeException {

    public CertificationNotFoundException(Long id) {
        super("Certification not found with id: " + id);
    }
}