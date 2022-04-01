package com.bookworms.digibooky.book.domain;

public class Book {
    private String ISBN;
    private String title;
    private String AuthorFirstName;
    private String AuthorLastName;

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return AuthorFirstName;
    }

    public String getAuthorLastName() {
        return AuthorLastName;
    }

    public String getISBN() {
        return this.ISBN;
    }
}
