package com.ektasingh.portfolio.search.dto.response;

import com.ektasingh.portfolio.blog.dto.response.BlogResponse;
import com.ektasingh.portfolio.project.dto.response.ProjectResponse;
import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.skill.dto.response.SkillResponse;
import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResultResponse {

    private SearchSectionResponse<ProjectResponse> projects;

    private SearchSectionResponse<BlogResponse> blogs;

    private SearchSectionResponse<SkillResponse> skills;

    private SearchSectionResponse<TechnologyResponse> technologies;

    private SearchSectionResponse<PublicationResponse> publications;

}