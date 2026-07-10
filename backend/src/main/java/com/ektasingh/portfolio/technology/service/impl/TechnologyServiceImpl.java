package com.ektasingh.portfolio.technology.service.impl;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.technology.dto.request.TechnologyCreateRequest;
import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;
import com.ektasingh.portfolio.technology.entity.Technology;
import com.ektasingh.portfolio.technology.exception.TechnologyNotFoundException;
import com.ektasingh.portfolio.technology.mapper.TechnologyMapper;
import com.ektasingh.portfolio.technology.repository.TechnologyRepository;
import com.ektasingh.portfolio.technology.service.TechnologyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository repository;
    private final TechnologyMapper mapper;

    @Override
    public TechnologyResponse createTechnology(TechnologyCreateRequest request) {

        Technology technology = mapper.toEntity(request);

        Technology savedTechnology = repository.save(technology);

        return mapper.toResponse(savedTechnology);
    }

    @Override
    public TechnologyResponse getTechnologyById(Long id) {

        Technology technology = repository.findById(id)
                .orElseThrow(() -> new TechnologyNotFoundException(id));

        return mapper.toResponse(technology);
    }

    @Override
    public List<TechnologyResponse> getAllTechnologies() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public TechnologyResponse updateTechnology(
            Long id,
            TechnologyCreateRequest request) {

        Technology technology = repository.findById(id)
                .orElseThrow(() -> new TechnologyNotFoundException(id));

        mapper.updateEntity(technology, request);

        Technology updatedTechnology = repository.save(technology);

        return mapper.toResponse(updatedTechnology);
    }

    @Override
    public void deleteTechnology(Long id) {

        Technology technology = repository.findById(id)
                .orElseThrow(() -> new TechnologyNotFoundException(id));

        repository.delete(technology);
    }

    @Override
    public PageResponse<TechnologyResponse> getTechnologies(
            int page,
            int size,
            String query,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Technology> technologyPage;

        if (query == null || query.isBlank()) {
            technologyPage = repository.findAll(pageable);
        } else {
            technologyPage = repository.searchTechnologiesPage(query, pageable);
        }

        PageResponse<TechnologyResponse> response = new PageResponse<>();

        response.setContent(
                technologyPage.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList());

        response.setPage(technologyPage.getNumber());

        response.setSize(technologyPage.getSize());

        response.setTotalElements(
                technologyPage.getTotalElements());

        response.setTotalPages(
                technologyPage.getTotalPages());

        response.setFirst(
                technologyPage.isFirst());

        response.setLast(
                technologyPage.isLast());

        response.setEmpty(
                technologyPage.isEmpty());

        return response;
    }

}