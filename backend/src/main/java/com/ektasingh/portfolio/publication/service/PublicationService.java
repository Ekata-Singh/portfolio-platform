package com.ektasingh.portfolio.publication.service;

import com.ektasingh.portfolio.publication.dto.request.PublicationCreateRequest;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.common.dto.response.PageResponse;

import java.util.List;

public interface PublicationService {

    PublicationResponse createPublication(PublicationCreateRequest request);

    PublicationResponse getPublicationById(Long id);

    List<PublicationResponse> getAllPublications();

    PageResponse<PublicationResponse> getPublications(
            int page,
            int size,
            String query,
            String sortBy,
            String direction
    );

    PublicationResponse updatePublication(Long id,
                                          PublicationCreateRequest request);

    void deletePublication(Long id);

}