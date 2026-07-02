package com.ektasingh.portfolio.publication.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.publication.dto.request.PublicationCreateRequest;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.publication.service.PublicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publication")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping
    public ResponseEntity<PublicationResponse> createPublication(
            @Valid @RequestBody PublicationCreateRequest request) {

        PublicationResponse response =
                publicationService.createPublication(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationResponse> getPublicationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                publicationService.getPublicationById(id));
    }

    @GetMapping
    public ResponseEntity<List<PublicationResponse>> getAllPublications() {

        return ResponseEntity.ok(
                publicationService.getAllPublications());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationResponse> updatePublication(
            @PathVariable Long id,
            @Valid @RequestBody PublicationCreateRequest request) {

        return ResponseEntity.ok(
                publicationService.updatePublication(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(
            @PathVariable Long id) {

        publicationService.deletePublication(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
        public ResponseEntity<PageResponse<PublicationResponse>> getPublications(

                @RequestParam(defaultValue = "0") int page,

                @RequestParam(defaultValue = "10") int size,

                @RequestParam(required = false) String query,

                @RequestParam(defaultValue = "displayOrder") String sortBy,

                @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                publicationService.getPublications(
                        page,
                        size,
                        query,
                        sortBy,
                        direction));
        }
}