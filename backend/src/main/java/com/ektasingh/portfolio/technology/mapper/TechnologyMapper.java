package com.ektasingh.portfolio.technology.mapper;

import com.ektasingh.portfolio.technology.dto.request.TechnologyCreateRequest;
import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;
import com.ektasingh.portfolio.technology.entity.Technology;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TechnologyMapper {

    public Technology toEntity(TechnologyCreateRequest request) {

        Technology technology = new Technology();

        technology.setTechnologyName(request.getTechnologyName());
        technology.setCategory(request.getCategory());
        technology.setIconUrl(request.getIconUrl());
        technology.setProficiency(request.getProficiency());
        technology.setDisplayOrder(request.getDisplayOrder());

        technology.setCreatedAt(LocalDateTime.now());
        technology.setUpdatedAt(LocalDateTime.now());

        return technology;
    }

    public TechnologyResponse toResponse(Technology technology) {

        TechnologyResponse response = new TechnologyResponse();

        response.setId(technology.getId());
        response.setTechnologyName(technology.getTechnologyName());
        response.setCategory(technology.getCategory());
        response.setIconUrl(technology.getIconUrl());
        response.setProficiency(technology.getProficiency());
        response.setDisplayOrder(technology.getDisplayOrder());
        response.setCreatedAt(technology.getCreatedAt());
        response.setUpdatedAt(technology.getUpdatedAt());

        return response;
    }

    public void updateEntity(
            Technology technology,
            TechnologyCreateRequest request) {

        technology.setTechnologyName(request.getTechnologyName());
        technology.setCategory(request.getCategory());
        technology.setIconUrl(request.getIconUrl());
        technology.setProficiency(request.getProficiency());
        technology.setDisplayOrder(request.getDisplayOrder());

        technology.setUpdatedAt(LocalDateTime.now());
    }
}