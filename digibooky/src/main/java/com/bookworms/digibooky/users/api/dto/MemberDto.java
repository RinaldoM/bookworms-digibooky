package com.bookworms.digibooky.users.api.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDto memberDto = (MemberDto) o;
        return postalCode == memberDto.postalCode && Objects.equals(id, memberDto.id) && Objects.equals(firstName, memberDto.firstName) && Objects.equals(lastName, memberDto.lastName) && Objects.equals(email, memberDto.email) && Objects.equals(streetName, memberDto.streetName) && Objects.equals(streetNumber, memberDto.streetNumber) && Objects.equals(city, memberDto.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, streetName, streetNumber, postalCode, city);
    }
}
