package com.ektasingh.portfolio.publication.service;

import com.ektasingh.portfolio.publication.dto.request.PublicationCreateRequest;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;

import java.util.List;

public interface PublicationService {

    PublicationResponse createPublication(PublicationCreateRequest request);

    PublicationResponse getPublicationById(Long id);

    List<PublicationResponse> getAllPublications();

    PublicationResponse updatePublication(Long id,
                                          PublicationCreateRequest request);

    void deletePublication(Long id);
}