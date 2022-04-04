package com.bookworms.digibooky.users.api;

public class CreateMemberDto {

    private String inss;
    private String firstName;
    private String lastName;
    private String email;
    private String streetName;
    private String streetNumber;
    private int postalCode;
    private String city;

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
