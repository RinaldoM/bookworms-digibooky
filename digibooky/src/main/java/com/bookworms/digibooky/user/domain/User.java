package com.bookworms.digibooky.user.domain;

import com.bookworms.digibooky.security.Role;

import java.util.UUID;

public abstract class User {
    private final String id;
    private final String lastName;
    private final String firstName;
    private final String email;
    private final Role role;

    public User(String lastName, String firstName, String email, Role role) {
        this.role = role;
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

    public Role getRole() {
        return role;
    }

}
