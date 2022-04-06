package com.bookworms.digibooky.user.api.dto;

public class CreateLibrarianDto {

    private final String lastName;
    private final String firstName;
    private final String email;

    public CreateLibrarianDto(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
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
