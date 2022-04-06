package com.bookworms.digibooky.user.domain;

import com.bookworms.digibooky.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public abstract class User {
    private final Logger userLogger = LoggerFactory.getLogger(User.class);

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
        userLogger.info("user created with id " + id);
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