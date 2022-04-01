package com.bookworms.digibooky.book.service;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.domain.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {
    public List<BookDto> toMovieDTO(List<Book> bookList) {
        return null;
    }
}
