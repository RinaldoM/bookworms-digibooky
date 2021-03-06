package com.bookworms.digibooky.user.domain;

import com.bookworms.digibooky.security.Role;

public class Librarian extends User {

    public Librarian(String lastName, String firstName, String email) {
        super(lastName, firstName, email, Role.LIBRARIAN);
    }
}
