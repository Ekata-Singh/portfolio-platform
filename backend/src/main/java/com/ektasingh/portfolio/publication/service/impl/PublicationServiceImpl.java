package com.ektasingh.portfolio.publication.service.impl;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.publication.dto.request.PublicationCreateRequest;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.publication.entity.Publication;
import com.ektasingh.portfolio.publication.exception.PublicationNotFoundException;
import com.ektasingh.portfolio.publication.mapper.PublicationMapper;
import com.ektasingh.portfolio.publication.repository.PublicationRepository;
import com.ektasingh.portfolio.publication.service.PublicationService;
import com.ektasingh.portfolio.storage.FileStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository repository;
    private final PublicationMapper mapper;
    private final FileStorageService fileStorageService;

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
    public PageResponse<PublicationResponse> getPublications(
            int page,
            int size,
            String query,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Publication> publicationPage;

        if (query != null && !query.isBlank()) {
            publicationPage = repository.searchPublications(query, pageable);
        } else {
            publicationPage = repository.findAll(pageable);
        }

        PageResponse<PublicationResponse> response = new PageResponse<>();

        response.setContent(
                publicationPage.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList());

        response.setPage(publicationPage.getNumber());

        response.setSize(publicationPage.getSize());

        response.setTotalElements(publicationPage.getTotalElements());

        response.setTotalPages(publicationPage.getTotalPages());

        response.setFirst(publicationPage.isFirst());

        response.setLast(publicationPage.isLast());

        response.setEmpty(publicationPage.isEmpty());

        return response;
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

    @Override
    public PublicationResponse uploadThumbnail(Long id, MultipartFile file) {

        Publication publication = repository.findById(id)
                .orElseThrow(() -> new PublicationNotFoundException(id));

        String thumbnailPath = fileStorageService.storePublicationThumbnail(file);

        publication.setThumbnailUrl(thumbnailPath);

        Publication savedPublication = repository.save(publication);

        return mapper.toResponse(savedPublication);
    }
}