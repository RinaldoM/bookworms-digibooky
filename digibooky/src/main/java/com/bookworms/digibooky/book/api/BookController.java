package com.bookworms.digibooky.book.api;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.api.dto.UpdateBookDto;
import com.bookworms.digibooky.book.service.BookService;
import com.bookworms.digibooky.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.bookworms.digibooky.security.Feature.*;

@RestController
@RequestMapping(path = "books")
public class BookController {

    private final BookService bookService;
    private final SecurityService securityService;

    public BookController(BookService bookService, SecurityService securityService) {
        this.bookService = bookService;
        this.securityService = securityService;
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
    @GetMapping(path="author/{author}" , produces ="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> searchBooksThatContainsAuthor(@PathVariable String author){
        return bookService.searchBooksThatContainsAuthor(author);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto registerNewBook(@RequestHeader String authorizationId, @RequestBody BookDto bookDto) {
        securityService.validateAuthorization(authorizationId, REGISTER_BOOK);
        return bookService.registerNewBook(bookDto);
    }

    @PutMapping(path = "/{isbn}", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@RequestHeader String authorizationId, @PathVariable String isbn, @RequestBody UpdateBookDto updateBookDto) {
        securityService.validateAuthorization(authorizationId, UPDATE_BOOK);
        return bookService.updateBook(isbn, updateBookDto);
    }

    @PutMapping(path = "delete/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto softDeleteBook(@RequestHeader String authorizationId, @PathVariable String isbn){
        securityService.validateAuthorization(authorizationId, DELETE_BOOK);
        return bookService.changeActiveState(isbn);
    }

    @PutMapping(path = "restore/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto restoreBook(@RequestHeader String authorizationId, @PathVariable String isbn){
        securityService.validateAuthorization(authorizationId, UPDATE_BOOK);
        return bookService.changeActiveState(isbn);
    }


}
