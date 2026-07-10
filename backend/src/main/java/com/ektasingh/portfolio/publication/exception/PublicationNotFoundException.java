package com.ektasingh.portfolio.publication.exception;

public class PublicationNotFoundException extends RuntimeException {

    public PublicationNotFoundException(Long id) {
        super("Publication not found with id: " + id);
    }
}