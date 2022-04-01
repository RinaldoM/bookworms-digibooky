package com.bookworms.digibooky.book.api.dto;

public class BookDto {
    private String ISBN;
    private String title;
    private String AuthorFirstName;
    private String AuthorLastName;

    public BookDto(String ISBN, String title, String authorFirstName, String authorLastName) {
        this.ISBN = ISBN;
        this.title = title;
        AuthorFirstName = authorFirstName;
        AuthorLastName = authorLastName;
    }
}
