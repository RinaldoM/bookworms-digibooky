package com.bookworms.digibooky.user.domain;

import com.bookworms.digibooky.security.Role;

public class Admin extends User{
    public Admin(String lastName, String firstName, String email) {
        super(lastName, firstName, email, Role.ADMIN);
    }
}
