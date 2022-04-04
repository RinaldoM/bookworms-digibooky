package com.bookworms.digibooky.users.api.dto;

import java.util.UUID;

public class LibrarianDto {

    private String id;
    private String lastName;
    private String firstName;
    private String email;

    public LibrarianDto(String id, String lastName, String firstName, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }
}
