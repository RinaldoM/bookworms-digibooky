package com.bookworms.digibooky.user.domain;

import com.bookworms.digibooky.security.Role;

public class Member extends User {
    private final String inss;
    private String streetName;
    private String streetNumber;
    private int postalCode;
    private final String city;

    public Member(String inss, String lastName, String email, String city) {
        super(lastName, null, email, Role.MEMBER);
        this.inss = inss;
        this.city = city;
    }

    public Member(String inss, String lastName, String firstName, String email, String streetName, String streetNumber, int postalCode, String city) {
        super(lastName, firstName, email, Role.MEMBER);
        this.inss = inss;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getInss() {
        return inss;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
