package com.bookworms.digibooky.book.api;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.book.service.BookMapper;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.http.ContentType.JSON;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Test
    void getAllBooks_BooksAreShownCorrectly() {

        List<Book> bookList = Lists.newArrayList(
                new Book("1", "HarryPotter", "JK", "Rowling"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin"));
        bookList.forEach(book -> bookRepository.save(book));

        BookDto[] result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        List<BookDto> expectedList = bookMapper.toDto(bookList);
        Assertions.assertThat(List.of(result)).hasSameElementsAs(expectedList);
    }

    @Test
    void getBookByISBN_BookisShownCorrectly() {

        List<Book> bookList = Lists.newArrayList(
                new Book("1", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
        bookList.forEach(book -> bookRepository.save(book));

        BookDto result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/books/2")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto.class);

        BookDto expectedBook = bookMapper.toDto(new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
        Assertions.assertThat(result).isEqualTo(expectedBook);
    }
}