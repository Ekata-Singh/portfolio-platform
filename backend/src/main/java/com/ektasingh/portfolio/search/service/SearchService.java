package com.ektasingh.portfolio.search.service;

import com.ektasingh.portfolio.search.dto.response.SearchResponse;

public interface SearchService {

    SearchResponse search(String query);

}