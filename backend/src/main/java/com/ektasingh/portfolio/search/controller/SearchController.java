package com.ektasingh.portfolio.search.controller;

import com.ektasingh.portfolio.search.dto.response.SearchResponse;
import com.ektasingh.portfolio.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@Tag(name = "Search", description = "Global Search APIs")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    @Operation(summary = "Search across Portfolio")
    public ResponseEntity<SearchResponse> search(
            @RequestParam String q) {

        return ResponseEntity.ok(
                searchService.search(q)
        );
    }
}