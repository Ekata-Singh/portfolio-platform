package com.ektasingh.portfolio.publication.service.impl;

import com.ektasingh.portfolio.publication.dto.request.PublicationCreateRequest;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.publication.entity.Publication;
import com.ektasingh.portfolio.publication.exception.PublicationNotFoundException;
import com.ektasingh.portfolio.publication.mapper.PublicationMapper;
import com.ektasingh.portfolio.publication.repository.PublicationRepository;
import com.ektasingh.portfolio.publication.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository repository;
    private final PublicationMapper mapper;

    @Override
    public PublicationResponse createPublication(PublicationCreateRequest request) {

        Publication publication = mapper.toEntity(request);

        Publication savedPublication = repository.save(publication);

        return mapper.toResponse(savedPublication);
    }

    @Override
    public PublicationResponse getPublicationById(Long id) {

        Publication publication = repository.findById(id)
                .orElseThrow(() -> new PublicationNotFoundException(id));

        return mapper.toResponse(publication);
    }

    @Override
    public List<PublicationResponse> getAllPublications() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PublicationResponse updatePublication(Long id,
                                                 PublicationCreateRequest request) {

        Publication publication = repository.findById(id)
                .orElseThrow(() -> new PublicationNotFoundException(id));

        mapper.updateEntity(publication, request);

        Publication updatedPublication = repository.save(publication);

        return mapper.toResponse(updatedPublication);
    }

    @Override
    public void deletePublication(Long id) {

        Publication publication = repository.findById(id)
                .orElseThrow(() -> new PublicationNotFoundException(id));

        repository.delete(publication);
    }
}