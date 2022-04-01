package com.bookworms.digibooky.book.service;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookService(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAll() {
        return bookMapper.toDto(bookRepository.getAll());
    }
}
