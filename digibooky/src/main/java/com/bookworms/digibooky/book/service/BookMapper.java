package com.bookworms.digibooky.book.service;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.domain.Book;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthorFirstName(), book.getAuthorLastName(), book.getSmallSummary());
    }

    public List<BookDto> toDto(Collection<Book> bookList) {
        return bookList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Book toBook(BookDto bookDto) {
        return new Book(bookDto.getIsbn(), bookDto.getTitle(), bookDto.getAuthorFirstName(), bookDto.getAuthorLastName(), bookDto.getSmallSummary());
    }
}
