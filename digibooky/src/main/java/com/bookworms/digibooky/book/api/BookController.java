package com.bookworms.digibooky.book.api;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping(path = "/{isbn}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @GetMapping(path="isbn/{isbn}" , produces ="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> searchBooksThatContainsIsbn(@PathVariable String isbn){
        return bookService.searchBooksThatContainsIsbn(isbn);
    }

    @GetMapping(path="title/{title}" , produces ="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> searchBooksThatContainsTitle(@PathVariable String title){
        return bookService.searchBooksThatContainsTitle(title);
    }


}
