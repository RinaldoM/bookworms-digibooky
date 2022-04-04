package com.bookworms.digibooky.book.service;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.domain.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final Logger serviceLogger = LoggerFactory.getLogger(BookService.class);

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookService(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAll() {
        serviceLogger.info("Showing all books to user.");
        return bookMapper.toDto(bookRepository.getAll());
    }

    public BookDto getBookByIsbn(String isbn) {
        serviceLogger.info("Showing book with ISBN " + isbn + " to user.");
        return bookMapper.toDto(bookRepository.getBookByIsbn(isbn));
    }

    public List<BookDto> searchBooksThatContainsIsbn(String isbn) {
        serviceLogger.info("Showing all books to the user that contains " + isbn + " in the ISBN.");

        return bookMapper.toDto(bookRepository.getAll())
                .stream()
                .filter(bookDto -> bookDto.getIsbn().contains(isbn))
                .collect(Collectors.toList());
    }

    public List<BookDto> searchBooksThatContainsTitle(String title) {
        serviceLogger.info("Showing all books to the user that contains '" + title + "' in the title.");

        return bookMapper.toDto(bookRepository.getAll())
                .stream()
                .filter(bookDto -> bookDto.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<BookDto> searchBooksThatContainsAuthor(String author) {
        serviceLogger.info("Showing all books to the user that contains '" + author + "' in the author's name.");

        return bookMapper.toDto(bookRepository.getAll())
                .stream()
                .filter(bookDto -> bookDto.authorsFullName().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }
}
