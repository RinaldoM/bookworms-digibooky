package com.bookworms.digibooky.rental.domain;

import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.user.domain.Member;

import java.time.LocalDate;
import java.util.UUID;

public class Rental {
    private final String rentalId;
    private final Book book;
    private final Member member;
    private  LocalDate dueDate;

    public Rental(Book book, Member member) {
        this.rentalId = UUID.randomUUID().toString();
        this.book = book;
        this.member = member;
        this.dueDate = LocalDate.now().plusWeeks(3);
    }

    public String getRentalId() {
        return rentalId;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
