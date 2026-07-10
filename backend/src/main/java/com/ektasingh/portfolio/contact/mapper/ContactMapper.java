package com.ektasingh.portfolio.contact.mapper;

import com.ektasingh.portfolio.contact.dto.request.ContactCreateRequest;
import com.ektasingh.portfolio.contact.dto.response.ContactResponse;
import com.ektasingh.portfolio.contact.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toEntity(ContactCreateRequest request) {

        Contact contact = new Contact();

        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setAddress(request.getAddress());
        contact.setCity(request.getCity());
        contact.setState(request.getState());
        contact.setCountry(request.getCountry());
        contact.setPostalCode(request.getPostalCode());

        return contact;
    }

    public ContactResponse toResponse(Contact contact) {

        ContactResponse response = new ContactResponse();

        response.setId(contact.getId());
        response.setEmail(contact.getEmail());
        response.setPhone(contact.getPhone());
        response.setAddress(contact.getAddress());
        response.setCity(contact.getCity());
        response.setState(contact.getState());
        response.setCountry(contact.getCountry());
        response.setPostalCode(contact.getPostalCode());
        response.setCreatedAt(contact.getCreatedAt());
        response.setUpdatedAt(contact.getUpdatedAt());

        return response;
    }
}