package com.ektasingh.portfolio.publication.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.publication.dto.request.PublicationCreateRequest;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.publication.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publication")
@RequiredArgsConstructor
@Tag(name = "Publications", description = "Publication Management APIs")
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping
    @Operation(summary = "Create Publication")
    public ResponseEntity<PublicationResponse> createPublication(
            @Valid @RequestBody PublicationCreateRequest request) {

        PublicationResponse response =
                publicationService.createPublication(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Publication by ID")
    public ResponseEntity<PublicationResponse> getPublicationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                publicationService.getPublicationById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Publications")
    public ResponseEntity<List<PublicationResponse>> getAllPublications() {

        return ResponseEntity.ok(
                publicationService.getAllPublications());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Publication")
    public ResponseEntity<PublicationResponse> updatePublication(
            @PathVariable Long id,
            @Valid @RequestBody PublicationCreateRequest request) {

        return ResponseEntity.ok(
                publicationService.updatePublication(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Publication")
    public ResponseEntity<Void> deletePublication(
            @PathVariable Long id) {

        publicationService.deletePublication(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(
        value = "/{id}/thumbnail",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Upload Publication Thumbnail")
    public ResponseEntity<PublicationResponse> uploadThumbnail(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                publicationService.uploadThumbnail(id, file));
    }

    @GetMapping("/page")
        @Operation(summary = "Get Paginated Publications")
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