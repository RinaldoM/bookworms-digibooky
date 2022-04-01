package com.bookworms.digibooky.book.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BookRepository {
    private final Logger repositoryLogger = LoggerFactory.getLogger(BookRepository.class);
    private final Map<String, Book> booksByISBN;

    public BookRepository() {
        booksByISBN = new HashMap<>();
    }

    public Book save(Book book) {
        booksByISBN.put(book.getIsbn(), book);
        return book;
    }

    public Collection<Book> getAll() {
        return booksByISBN.values();
    }

    public Book getBookByIsbn(String isbn) {
        Book foundBook = booksByISBN.get(isbn);
        if (foundBook == null) {
            repositoryLogger.error("No book found for isbn " + isbn);
            throw new IllegalArgumentException("No book found for isbn " + isbn);
        }
        return foundBook;
    }
}
