package com.bookworms.digibooky.rental.api.dto;

import java.util.Objects;

public class CreateRentalDto {
    private final String userId;
    private final String bookIsbn;


    public CreateRentalDto(String userId, String bookIsbn) {
        this.userId = userId;
        this.bookIsbn = bookIsbn;
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
        CreateRentalDto that = (CreateRentalDto) o;
        return Objects.equals(userId, that.userId) && Objects.equals(bookIsbn, that.bookIsbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookIsbn);
    }


}
