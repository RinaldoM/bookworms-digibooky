package com.bookworms.digibooky.users.domain;

import java.util.UUID;

public class Librarian {

    private String id;
    private String lastName;
    private String firstName;
    private String email;

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
