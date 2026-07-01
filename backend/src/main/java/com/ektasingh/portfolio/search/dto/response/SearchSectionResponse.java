package com.ektasingh.portfolio.search.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchSectionResponse<T> {

    private long count;

    private List<T> items;

}