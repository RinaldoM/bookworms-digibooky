package com.bookworms.digibooky.user.domain;

import java.util.UUID;

public class Librarian {

    private final String id;
    private final String lastName;
    private final String firstName;
    private final String email;

    public Librarian(String lastName, String firstName, String email) {
        this.id = UUID.randomUUID().toString();
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
