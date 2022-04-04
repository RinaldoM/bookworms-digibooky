package com.bookworms.digibooky.rental.api.dto;

import java.util.Objects;

public class RentalDto {
    private final String rentalId;
    private final String userId;
    private final String bookIsbn;

    public RentalDto(String rentalId, String userId, String bookIsbn) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.bookIsbn = bookIsbn;
    }

    public String getRentalId() {
        return rentalId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDto rentalDto = (RentalDto) o;
        return Objects.equals(rentalId, rentalDto.rentalId) && Objects.equals(userId, rentalDto.userId) && Objects.equals(bookIsbn, rentalDto.bookIsbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, userId, bookIsbn);
    }
}
