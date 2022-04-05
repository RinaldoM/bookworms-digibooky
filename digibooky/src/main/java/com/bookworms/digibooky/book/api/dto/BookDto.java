package com.bookworms.digibooky.book.api.dto;

import java.util.Objects;

public class BookDto {
    private final String isbn;
    private final String title;
    private final String authorFirstName;
    private final String authorLastName;
    private final String smallSummary;

    public BookDto(String isbn, String title, String authorFirstName, String authorLastName, String smallSummary) {
        this.isbn = isbn;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.smallSummary = smallSummary;
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

    public String getSmallSummary() {
        return smallSummary;
    }

    public String authorsFullName(){
        return authorFirstName + " " + authorLastName;
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
