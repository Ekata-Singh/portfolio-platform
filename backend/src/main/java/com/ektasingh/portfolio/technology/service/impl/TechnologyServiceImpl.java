package com.ektasingh.portfolio.technology.service.impl;

import com.ektasingh.portfolio.technology.dto.request.TechnologyCreateRequest;
import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;
import com.ektasingh.portfolio.technology.entity.Technology;
import com.ektasingh.portfolio.technology.exception.TechnologyNotFoundException;
import com.ektasingh.portfolio.technology.mapper.TechnologyMapper;
import com.ektasingh.portfolio.technology.repository.TechnologyRepository;
import com.ektasingh.portfolio.technology.service.TechnologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}