package com.bookworms.digibooky.book.api.dto;

public class UpdateBookDto {
    private final String title;
    private final String authorFirstName;
    private final String authorLastName;
    private final String smallSummary;

    public UpdateBookDto(String title, String authorFirstName, String authorLastName, String smallSummary) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.smallSummary = smallSummary;
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
}
