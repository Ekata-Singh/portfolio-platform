package com.ektasingh.portfolio.contact.service;

import com.ektasingh.portfolio.contact.dto.request.ContactCreateRequest;
import com.ektasingh.portfolio.contact.dto.response.ContactResponse;
import com.ektasingh.portfolio.common.dto.response.PageResponse;
import java.util.List;

public interface ContactService {

    ContactResponse createContact(ContactCreateRequest request);

    ContactResponse getContactById(Long id);

    List<ContactResponse> getAllContacts();

    PageResponse<ContactResponse> getContacts(
        int page,
        int size,
        String query,
        String sortBy,
        String direction
);

    ContactResponse updateContact(Long id,
                                  ContactCreateRequest request);

    void deleteContact(Long id);

}