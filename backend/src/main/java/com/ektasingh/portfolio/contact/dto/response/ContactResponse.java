package com.ektasingh.portfolio.contact.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContactResponse {

    private Long id;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}