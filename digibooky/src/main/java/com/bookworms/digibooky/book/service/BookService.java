package com.bookworms.digibooky.book.service;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.domain.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private Logger serviceLogger = LoggerFactory.getLogger(BookService.class);

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
}
