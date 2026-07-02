package com.ektasingh.portfolio.technology.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.technology.dto.request.TechnologyCreateRequest;
import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;
import com.ektasingh.portfolio.technology.service.TechnologyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/technology")
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService technologyService;

    @PostMapping
    public ResponseEntity<TechnologyResponse> createTechnology(
            @Valid @RequestBody TechnologyCreateRequest request) {

        TechnologyResponse response =
                technologyService.createTechnology(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnologyResponse> getTechnologyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                technologyService.getTechnologyById(id));
    }

    @GetMapping
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies() {

        return ResponseEntity.ok(
                technologyService.getAllTechnologies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechnologyResponse> updateTechnology(
            @PathVariable Long id,
            @Valid @RequestBody TechnologyCreateRequest request) {

        return ResponseEntity.ok(
                technologyService.updateTechnology(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnology(
            @PathVariable Long id) {

        technologyService.deleteTechnology(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
        public ResponseEntity<PageResponse<TechnologyResponse>> getTechnologies(

                @RequestParam(defaultValue = "") String query,

                @RequestParam(defaultValue = "0") int page,

                @RequestParam(defaultValue = "10") int size,

                @RequestParam(defaultValue = "displayOrder") String sortBy,

                @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                technologyService.getTechnologies(
                        page,
                        size,
                        query,
                        sortBy,
                        direction));
        }
}