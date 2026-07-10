package com.ektasingh.portfolio.skill.service.impl;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.skill.dto.request.SkillCreateRequest;
import com.ektasingh.portfolio.skill.dto.response.SkillResponse;
import com.ektasingh.portfolio.skill.entity.Skill;
import com.ektasingh.portfolio.skill.exception.SkillNotFoundException;
import com.ektasingh.portfolio.skill.mapper.SkillMapper;
import com.ektasingh.portfolio.skill.repository.SkillRepository;
import com.ektasingh.portfolio.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repository;
    private final SkillMapper mapper;

    @Override
    public SkillResponse createSkill(SkillCreateRequest request) {

        Skill skill = mapper.toEntity(request);

        Skill savedSkill = repository.save(skill);

        return mapper.toResponse(savedSkill);
    }

    @Override
    public SkillResponse getSkillById(Long id) {

        Skill skill = repository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));

        return mapper.toResponse(skill);
    }

    @Override
    public List<SkillResponse> getAllSkills() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SkillResponse updateSkill(Long id,
                                     SkillCreateRequest request) {

        Skill skill = repository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));

        mapper.updateEntity(skill, request);

        Skill updatedSkill = repository.save(skill);

        return mapper.toResponse(updatedSkill);
    }

    @Override
    public void deleteSkill(Long id) {

        Skill skill = repository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));

        repository.delete(skill);
    }

    @Override
    public PageResponse<SkillResponse> getSkills(
            String query,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Skill> skillPage;

        if (query == null || query.trim().isEmpty()) {
            skillPage = repository.findAll(pageable);
        } else {
            skillPage = repository.searchSkillsPage(query, pageable);
        }

        PageResponse<SkillResponse> response = new PageResponse<>();

        response.setContent(
                skillPage.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList());

        response.setPage(skillPage.getNumber());
        response.setSize(skillPage.getSize());
        response.setTotalElements(skillPage.getTotalElements());
        response.setTotalPages(skillPage.getTotalPages());
        response.setFirst(skillPage.isFirst());
        response.setLast(skillPage.isLast());
        response.setEmpty(skillPage.isEmpty());

        return response;
    }

}