package com.ektasingh.portfolio.search.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResponse {

    private String query;

    private long totalResults;

    private SearchResultResponse results;

}