package com.bookworms.digibooky.book.api.dto;

import java.util.Objects;

public class BookDto {
    private String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;

    public BookDto(String isbn, String title, String authorFirstName, String authorLastName) {
        this.isbn = isbn;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(isbn, bookDto.isbn) && Objects.equals(title, bookDto.title) && Objects.equals(authorFirstName, bookDto.authorFirstName) && Objects.equals(authorLastName, bookDto.authorLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, authorFirstName, authorLastName);
    }
}
