package com.bookworms.digibooky.book.domain;

public class Book {
    private final String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String smallSummary;
    private boolean isRented;
    private boolean isActive;

    public Book(String isbn, String title, String authorFirstName, String authorLastName, String smallSummary) {
        this.isbn = isbn;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.smallSummary = smallSummary;
        this.isRented = false;
        this.isActive = true;
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

    public String getSmallSummary() {
        return smallSummary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public void setSmallSummary(String smallSummary) {
        this.smallSummary = smallSummary;
    }

    public void changeRentedState() {
        isRented = !isRented;
    }

    public boolean getRentedState() {
        return isRented;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive() {
        isActive = !isActive;
    }

}
