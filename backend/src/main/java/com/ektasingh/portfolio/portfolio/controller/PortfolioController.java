package com.ektasingh.portfolio.portfolio.controller;

import com.ektasingh.portfolio.portfolio.dto.response.PortfolioResponse;
import com.ektasingh.portfolio.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
@Tag(name = "Portfolio", description = "Portfolio Aggregation APIs")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    @Operation(summary = "Get Complete Portfolio")
    public ResponseEntity<PortfolioResponse> getPortfolio() {

        return ResponseEntity.ok(
                portfolioService.getPortfolio()
        );

    }
}