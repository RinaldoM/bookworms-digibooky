package com.bookworms.digibooky.book.domain;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BookRepository {
    private final Map<String, Book> booksByISBN;

    public BookRepository() {
        booksByISBN = new HashMap<>();
    }

    public Book save(Book book) {
        booksByISBN.put(book.getISBN(), book);
        return book;
    }
}
