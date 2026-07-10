package com.ektasingh.portfolio.technology.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.technology.dto.request.TechnologyCreateRequest;
import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;
import com.ektasingh.portfolio.technology.service.TechnologyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/technology")
@RequiredArgsConstructor
@Tag(name = "Technology", description = "Technology Management APIs")
public class TechnologyController {

    private final TechnologyService technologyService;

    @PostMapping
    @Operation(summary = "Create Technology")
    public ResponseEntity<TechnologyResponse> createTechnology(
            @Valid @RequestBody TechnologyCreateRequest request) {

        TechnologyResponse response =
                technologyService.createTechnology(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Technology by ID")
    public ResponseEntity<TechnologyResponse> getTechnologyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                technologyService.getTechnologyById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Technologies")
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies() {

        return ResponseEntity.ok(
                technologyService.getAllTechnologies());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Technology")
    public ResponseEntity<TechnologyResponse> updateTechnology(
            @PathVariable Long id,
            @Valid @RequestBody TechnologyCreateRequest request) {

        return ResponseEntity.ok(
                technologyService.updateTechnology(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Technology")
    public ResponseEntity<Void> deleteTechnology(
            @PathVariable Long id) {

        technologyService.deleteTechnology(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
        @Operation(summary = "Get Paginated Technologies")
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