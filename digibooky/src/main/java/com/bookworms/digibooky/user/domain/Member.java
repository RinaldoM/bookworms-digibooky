package com.bookworms.digibooky.user.domain;

import java.util.UUID;

public class Member {
    private final String id;
    private final String inss;
    private String firstName;
    private final String lastName;
    private final String email;
    private String streetName;
    private String streetNumber;
    private int postalCode;
    private final String city;


    public Member(String inss, String lastName, String email, String city) {
        this.id = UUID.randomUUID().toString();
        this.inss = inss;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
    }

    public Member(String inss, String firstName, String lastName, String email, String streetName, String streetNumber, int postalCode, String city) {
        this.id = UUID.randomUUID().toString();
        this.inss = inss;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public String getInss() {
        return inss;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
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
