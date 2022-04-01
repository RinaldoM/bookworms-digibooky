package com.bookworms.digibooky.users.api.dto;

import java.util.UUID;

public class MemberDto {

        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String streetName;
        private String streetNumber;
        private int postalCode;
        private String city;

    public String getId() {
        return id;
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

    public MemberDto(String id, String firstName, String lastName, String email, String streetName, String streetNumber, int postalCode, String city) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.streetName = streetName;
            this.streetNumber = streetNumber;
            this.postalCode = postalCode;
            this.city = city;
        }
}
