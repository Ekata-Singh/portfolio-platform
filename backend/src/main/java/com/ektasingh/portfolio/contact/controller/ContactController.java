package com.ektasingh.portfolio.contact.controller;

import com.ektasingh.portfolio.common.dto.response.PageResponse;
import com.ektasingh.portfolio.contact.dto.request.ContactCreateRequest;
import com.ektasingh.portfolio.contact.dto.response.ContactResponse;
import com.ektasingh.portfolio.contact.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact")
@Tag(name = "Contact", description = "Contact Management APIs")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Contact")
    public ContactResponse createContact(
            @Valid @RequestBody ContactCreateRequest request) {

        return contactService.createContact(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Contact by ID")
    public ContactResponse getContactById(@PathVariable Long id) {

        return contactService.getContactById(id);
    }

    @GetMapping
    @Operation(summary = "Get All Contacts")
    public List<ContactResponse> getAllContacts() {

        return contactService.getAllContacts();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Contact")
    public ContactResponse updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactCreateRequest request) {

        return contactService.updateContact(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Contact")
    public void deleteContact(@PathVariable Long id) {

        contactService.deleteContact(id);
    }

    @GetMapping("/page")
    @Operation(summary = "Get Paginated Contacts")
    public ResponseEntity<PageResponse<ContactResponse>> getContacts(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(required = false) String query,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                contactService.getContacts(
                        page,
                        size,
                        query,
                        sortBy,
                        direction
                ));
    }
}