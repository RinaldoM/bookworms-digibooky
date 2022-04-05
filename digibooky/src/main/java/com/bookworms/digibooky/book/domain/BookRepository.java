package com.bookworms.digibooky.book.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        return booksByISBN.values()
                .stream()
                .filter(Book::isActive).collect(Collectors.toList());
    }

    public Book getBookByIsbn(String isbn) {
        Book foundBook = booksByISBN.get(isbn);
        if (foundBook == null) {
            repositoryLogger.error("No book found for isbn " + isbn);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//            throw new IllegalArgumentException("No book found for isbn " + isbn);
        }
        return foundBook;
    }

    public void saveBook(Book book) {
        booksByISBN.put(book.getIsbn(), book);
    }
}
