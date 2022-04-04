package com.bookworms.digibooky.user.api.dto;

public class LibrarianDto {

    private final String id;
    private final String lastName;
    private final String firstName;
    private final String email;

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
