package com.bookworms.digibooky.book.service;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.api.dto.UpdateBookDto;
import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.user.api.dto.CreateMemberDto;
import com.bookworms.digibooky.user.domain.Librarian;
import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public BookDto registerNewBook(BookDto bookDto) {
        isbnValidation(bookDto);
        validateAndCheckLoggingMessage(isEmptyNullSafe(bookDto.getTitle()), "No title filled in to register a book!", "Proper title introduced.");
        validateAndCheckLoggingMessage(isEmptyNullSafe(bookDto.getAuthorLastName()), "No last name of the author filled in to register a book!", "Proper author's last name introduced.");

        Book book = bookMapper.toBook(bookDto);
        serviceLogger.info("New book getting registered.");
        bookRepository.saveBook(book);
        return bookMapper.toDto(book);
    }

    private void isbnValidation(BookDto bookDto) {
        validateAndCheckLoggingMessage(isEmptyNullSafe(bookDto.getIsbn()), "No isbn filled in to register a book!", "Proper isbn number introduced.");
    }

    private void validateAndCheckLoggingMessage(boolean condition, String errorMessage, String confirmationMessage) {
        if (condition) {
            serviceLogger.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        serviceLogger.info(confirmationMessage);
    }

    private boolean isEmptyNullSafe(String string) {
        return string == null || string.isEmpty();
    }

    public BookDto updateBook(String isbn, UpdateBookDto updateBookDto) {
        Book foundBook = bookRepository.getBookByIsbn(isbn);
        foundBook.setTitle(updateBookDto.getTitle());
        foundBook.setAuthorFirstName(updateBookDto.getAuthorFirstName());
        foundBook.setAuthorLastName(updateBookDto.getAuthorLastName());
        foundBook.setSmallSummary(updateBookDto.getSmallSummary());
        return bookMapper.toDto(foundBook);
    }
}
