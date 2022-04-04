package com.bookworms.digibooky.user.api.dto;

public class CreateMemberDto {

    private final String inss;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String streetName;
    private final String streetNumber;
    private final int postalCode;
    private final String city;

    public CreateMemberDto(String inss, String firstName, String lastName, String email, String streetName, String streetNumber, int postalCode, String city) {
        this.inss = inss;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
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
