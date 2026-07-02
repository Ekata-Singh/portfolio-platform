package com.ektasingh.portfolio.certificate.service.impl;

import com.ektasingh.portfolio.certificate.dto.request.CertificationCreateRequest;
import com.ektasingh.portfolio.certificate.dto.response.CertificationResponse;
import com.ektasingh.portfolio.certificate.entity.Certification;
import com.ektasingh.portfolio.certificate.exception.CertificationNotFoundException;
import com.ektasingh.portfolio.certificate.mapper.CertificationMapper;
import com.ektasingh.portfolio.certificate.repository.CertificationRepository;
import com.ektasingh.portfolio.certificate.service.CertificationService;
import com.ektasingh.portfolio.common.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository repository;
    private final CertificationMapper mapper;

    @Override
    public CertificationResponse createCertification(CertificationCreateRequest request) {

        Certification certification = mapper.toEntity(request);

        Certification savedCertification = repository.save(certification);

        return mapper.toResponse(savedCertification);
    }

    @Override
    public CertificationResponse getCertificationById(Long id) {

        Certification certification = repository.findById(id)
                .orElseThrow(() -> new CertificationNotFoundException(id));

        return mapper.toResponse(certification);
    }

    @Override
    public List<CertificationResponse> getAllCertifications() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CertificationResponse updateCertification(
            Long id,
            CertificationCreateRequest request) {

        Certification certification = repository.findById(id)
                .orElseThrow(() -> new CertificationNotFoundException(id));

        mapper.updateEntity(certification, request);

        Certification updatedCertification = repository.save(certification);

        return mapper.toResponse(updatedCertification);
    }

    @Override
    public void deleteCertification(Long id) {

            Certification certification = repository.findById(id)
                    .orElseThrow(() -> new CertificationNotFoundException(id));

            repository.delete(certification);
        }

    @Override
    public PageResponse<CertificationResponse> getCertifications(
            int page,
            int size,
            String query,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Certification> certificationPage;

        if (query != null && !query.isBlank()) {
            certificationPage =
                    repository.searchCertifications(query, pageable);
        } else {
            certificationPage =
                    repository.findAll(pageable);
        }

        PageResponse<CertificationResponse> response = new PageResponse<>();

        response.setContent(
                certificationPage.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList());

        response.setPage(certificationPage.getNumber());
        response.setSize(certificationPage.getSize());
        response.setTotalElements(certificationPage.getTotalElements());
        response.setTotalPages(certificationPage.getTotalPages());
        response.setFirst(certificationPage.isFirst());
        response.setLast(certificationPage.isLast());
        response.setEmpty(certificationPage.isEmpty());

        return response;
    }


}