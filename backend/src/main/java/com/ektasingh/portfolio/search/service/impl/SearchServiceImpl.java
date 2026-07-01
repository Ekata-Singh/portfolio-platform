package com.ektasingh.portfolio.search.service.impl;

import com.ektasingh.portfolio.blog.dto.response.BlogResponse;
import com.ektasingh.portfolio.blog.entity.Blog;
import com.ektasingh.portfolio.blog.mapper.BlogMapper;
import com.ektasingh.portfolio.blog.repository.BlogRepository;

import com.ektasingh.portfolio.project.dto.response.ProjectResponse;
import com.ektasingh.portfolio.project.entity.Project;
import com.ektasingh.portfolio.project.mapper.ProjectMapper;
import com.ektasingh.portfolio.project.repository.ProjectRepository;

import com.ektasingh.portfolio.publication.dto.response.PublicationResponse;
import com.ektasingh.portfolio.publication.entity.Publication;
import com.ektasingh.portfolio.publication.mapper.PublicationMapper;
import com.ektasingh.portfolio.publication.repository.PublicationRepository;

import com.ektasingh.portfolio.search.dto.response.SearchResponse;
import com.ektasingh.portfolio.search.dto.response.SearchResultResponse;
import com.ektasingh.portfolio.search.dto.response.SearchSectionResponse;
import com.ektasingh.portfolio.search.service.SearchService;

import com.ektasingh.portfolio.skill.dto.response.SkillResponse;
import com.ektasingh.portfolio.skill.entity.Skill;
import com.ektasingh.portfolio.skill.mapper.SkillMapper;
import com.ektasingh.portfolio.skill.repository.SkillRepository;

import com.ektasingh.portfolio.technology.dto.response.TechnologyResponse;
import com.ektasingh.portfolio.technology.entity.Technology;
import com.ektasingh.portfolio.technology.mapper.TechnologyMapper;
import com.ektasingh.portfolio.technology.repository.TechnologyRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final ProjectRepository projectRepository;
    private final BlogRepository blogRepository;
    private final SkillRepository skillRepository;
    private final TechnologyRepository technologyRepository;
    private final PublicationRepository publicationRepository;

    private final ProjectMapper projectMapper;
    private final BlogMapper blogMapper;
    private final SkillMapper skillMapper;
    private final TechnologyMapper technologyMapper;
    private final PublicationMapper publicationMapper;

    public SearchServiceImpl(
            ProjectRepository projectRepository,
            BlogRepository blogRepository,
            SkillRepository skillRepository,
            TechnologyRepository technologyRepository,
            PublicationRepository publicationRepository,
            ProjectMapper projectMapper,
            BlogMapper blogMapper,
            SkillMapper skillMapper,
            TechnologyMapper technologyMapper,
            PublicationMapper publicationMapper) {

        this.projectRepository = projectRepository;
        this.blogRepository = blogRepository;
        this.skillRepository = skillRepository;
        this.technologyRepository = technologyRepository;
        this.publicationRepository = publicationRepository;

        this.projectMapper = projectMapper;
        this.blogMapper = blogMapper;
        this.skillMapper = skillMapper;
        this.technologyMapper = technologyMapper;
        this.publicationMapper = publicationMapper;
    }

    @Override
    public SearchResponse search(String query) {

        List<Project> projects =
                projectRepository.searchProjects(query);

        List<Blog> blogs =
                blogRepository.searchBlogs(query);

        List<Skill> skills =
                skillRepository.searchSkills(query);

        List<Technology> technologies =
                technologyRepository.searchTechnologies(query);

        List<Publication> publications =
                publicationRepository.searchPublications(query);

        SearchSectionResponse<ProjectResponse> projectSection =
                new SearchSectionResponse<>();

        projectSection.setItems(
                projects.stream()
                        .map(projectMapper::toResponse)
                        .toList());

        projectSection.setCount(projects.size());

        SearchSectionResponse<BlogResponse> blogSection =
                new SearchSectionResponse<>();

        blogSection.setItems(
                blogs.stream()
                        .map(blogMapper::toResponse)
                        .toList());

        blogSection.setCount(blogs.size());

        SearchSectionResponse<SkillResponse> skillSection =
                new SearchSectionResponse<>();

        skillSection.setItems(
                skills.stream()
                        .map(skillMapper::toResponse)
                        .toList());

        skillSection.setCount(skills.size());

        SearchSectionResponse<TechnologyResponse> technologySection =
                new SearchSectionResponse<>();

        technologySection.setItems(
                technologies.stream()
                        .map(technologyMapper::toResponse)
                        .toList());

        technologySection.setCount(technologies.size());

        SearchSectionResponse<PublicationResponse> publicationSection =
                new SearchSectionResponse<>();

        publicationSection.setItems(
                publications.stream()
                        .map(publicationMapper::toResponse)
                        .toList());

        publicationSection.setCount(publications.size());

        SearchResultResponse results = new SearchResultResponse();

        results.setProjects(projectSection);
        results.setBlogs(blogSection);
        results.setSkills(skillSection);
        results.setTechnologies(technologySection);
        results.setPublications(publicationSection);

        SearchResponse response = new SearchResponse();

        response.setQuery(query);

        response.setResults(results);

        response.setTotalResults(
                projects.size()
                        + blogs.size()
                        + skills.size()
                        + technologies.size()
                        + publications.size());

        return response;
    }
}