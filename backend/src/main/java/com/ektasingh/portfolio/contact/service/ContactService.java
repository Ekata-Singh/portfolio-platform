package com.ektasingh.portfolio.contact.service;

import com.ektasingh.portfolio.contact.dto.request.ContactCreateRequest;
import com.ektasingh.portfolio.contact.dto.response.ContactResponse;

import java.util.List;

public interface ContactService {

    ContactResponse createContact(ContactCreateRequest request);

    ContactResponse getContactById(Long id);

    List<ContactResponse> getAllContacts();

    ContactResponse updateContact(Long id, ContactCreateRequest request);

    void deleteContact(Long id);
}