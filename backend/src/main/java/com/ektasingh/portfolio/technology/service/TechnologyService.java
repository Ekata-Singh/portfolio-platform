package com.ektasingh.portfolio.technology.service;

import com.ektasingh.portfolio.technology.dto.request.TechnologyCreateRequest;
import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;

import java.util.List;

public interface TechnologyService {

    TechnologyResponse createTechnology(TechnologyCreateRequest request);

    TechnologyResponse getTechnologyById(Long id);

    List<TechnologyResponse> getAllTechnologies();

    TechnologyResponse updateTechnology(
            Long id,
            TechnologyCreateRequest request);

    void deleteTechnology(Long id);
}