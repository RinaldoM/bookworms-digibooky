package com.bookworms.digibooky.book.domain;

public class Book {
    private final String isbn;
    private final String title;
    private final String authorFirstName;
    private final String authorLastName;
    private final String shortSummary;

    public Book(String isbn, String title, String authorFirstName, String authorLastName, String shortSummary) {
        this.isbn = isbn;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.shortSummary = shortSummary;
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

    public String getIsbn() {
        return this.isbn;
    }

    public String getShortSummary() {
        return shortSummary;
    }

  
}
