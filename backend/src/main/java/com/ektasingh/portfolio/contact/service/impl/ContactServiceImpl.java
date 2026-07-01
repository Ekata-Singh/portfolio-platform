package com.ektasingh.portfolio.contact.service.impl;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.contact.dto.request.ContactCreateRequest;
import com.ektasingh.portfolio.contact.dto.response.ContactResponse;
import com.ektasingh.portfolio.contact.entity.Contact;
import com.ektasingh.portfolio.contact.exception.ContactNotFoundException;
import com.ektasingh.portfolio.contact.mapper.ContactMapper;
import com.ektasingh.portfolio.contact.repository.ContactRepository;
import com.ektasingh.portfolio.contact.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;
    private final ContactMapper mapper;

    public ContactServiceImpl(ContactRepository repository,
                              ContactMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ContactResponse createContact(ContactCreateRequest request) {

        Contact contact = mapper.toEntity(request);

        Contact savedContact = repository.save(contact);

        return mapper.toResponse(savedContact);
    }

    @Override
    public ContactResponse getContactById(Long id) {

        Contact contact = repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));

        return mapper.toResponse(contact);
    }

    @Override
    public List<ContactResponse> getAllContacts() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ContactResponse updateContact(Long id,
                                         ContactCreateRequest request) {

        Contact contact = repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));

        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setAddress(request.getAddress());
        contact.setCity(request.getCity());
        contact.setState(request.getState());
        contact.setCountry(request.getCountry());
        contact.setPostalCode(request.getPostalCode());

        Contact updatedContact = repository.save(contact);

        return mapper.toResponse(updatedContact);
    }

    @Override
    public void deleteContact(Long id) {

        Contact contact = repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));

        repository.delete(contact);
    }

    @Override
    public PageResponse<ContactResponse> getContacts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Contact> contactPage =
                repository.findAllByOrderByIdAsc(pageable);

        PageResponse<ContactResponse> response = new PageResponse<>();

        response.setContent(
                contactPage.getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );

        response.setPage(contactPage.getNumber());

        response.setSize(contactPage.getSize());

        response.setTotalElements(contactPage.getTotalElements());

        response.setTotalPages(contactPage.getTotalPages());

        response.setFirst(contactPage.isFirst());

        response.setLast(contactPage.isLast());

        response.setEmpty(contactPage.isEmpty());

        return response;
    }
}