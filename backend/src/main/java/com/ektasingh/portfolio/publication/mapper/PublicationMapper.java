package com.ektasingh.portfolio.publication.mapper;

import com.ektasingh.portfolio.publication.dto.request.PublicationCreateRequest;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.publication.entity.Publication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PublicationMapper {

    public Publication toEntity(PublicationCreateRequest request) {

        Publication publication = new Publication();

        publication.setTitle(request.getTitle());
        publication.setPublisher(request.getPublisher());
        publication.setPublicationDate(request.getPublicationDate());
        publication.setPublicationUrl(request.getPublicationUrl());
        publication.setDescription(request.getDescription());
        publication.setDisplayOrder(request.getDisplayOrder());

        publication.setCreatedAt(LocalDateTime.now());
        publication.setUpdatedAt(LocalDateTime.now());

        return publication;
    }

    public PublicationResponse toResponse(Publication publication) {

        PublicationResponse response = new PublicationResponse();

        response.setId(publication.getId());
        response.setTitle(publication.getTitle());
        response.setPublisher(publication.getPublisher());
        response.setPublicationDate(publication.getPublicationDate());
        response.setPublicationUrl(publication.getPublicationUrl());
        response.setDescription(publication.getDescription());
        response.setDisplayOrder(publication.getDisplayOrder());
        response.setCreatedAt(publication.getCreatedAt());
        response.setUpdatedAt(publication.getUpdatedAt());

        return response;
    }

    public void updateEntity(Publication publication,
                             PublicationCreateRequest request) {

        publication.setTitle(request.getTitle());
        publication.setPublisher(request.getPublisher());
        publication.setPublicationDate(request.getPublicationDate());
        publication.setPublicationUrl(request.getPublicationUrl());
        publication.setDescription(request.getDescription());
        publication.setDisplayOrder(request.getDisplayOrder());

        publication.setUpdatedAt(LocalDateTime.now());
    }
}