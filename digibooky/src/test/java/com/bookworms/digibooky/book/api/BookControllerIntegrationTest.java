package com.bookworms.digibooky.book.api;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.book.service.BookMapper;
import com.bookworms.digibooky.user.api.dto.MemberDto;
import com.bookworms.digibooky.user.domain.Member;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
                new Book("1", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
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
    void getBookByISBN_BookIsShownCorrectly() {

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

    @Test
    void getBookByISBN_IfWrongIsbnEnteredThenHandledCorrectly() {

        List<Book> bookList = Lists.newArrayList(
                new Book("1", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
        bookList.forEach(book -> bookRepository.save(book));

        RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/books/4")
                .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Assertions.assertThatThrownBy(() -> bookRepository.getBookByIsbn("4")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No book found for isbn 4");
    }

    @Test
    void getAllBooks_SearchBooksByISBN_BooksShownCorrectly() {

        List<Book> bookList = Lists.newArrayList(
                new Book("10", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("100", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("20", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));

        bookList.forEach(book -> bookRepository.save(book));

        BookDto[] result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/books/isbn/10")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        List<BookDto> expectedList = bookMapper.toDto(Lists.newArrayList(
                new Book("10", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("100", "HarryPotter", "JK", "Rowling", "A book about teen wizards")));
        Assertions.assertThat(List.of(result)).hasSameElementsAs(expectedList);
    }

    @Test
    void getAllBooks_SearchBooksByTitle_BooksShownCorrectly() {

        List<Book> bookList = Lists.newArrayList(
                new Book("10", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("100", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("20", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));

        bookList.forEach(book -> bookRepository.save(book));

        BookDto[] result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/books/title/Harry")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        List<BookDto> expectedList = bookMapper.toDto(Lists.newArrayList(
                new Book("10", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("100", "HarryPotter", "JK", "Rowling", "A book about teen wizards")));
        Assertions.assertThat(List.of(result)).hasSameElementsAs(expectedList);
    }

    @Test
    void getAllBooks_SearchBooksByAuthor_BooksShownCorrectly() {
        List<Book> bookList = Lists.newArrayList(
                new Book("10", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("100", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("20", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));

        bookList.forEach(book -> bookRepository.save(book));

        BookDto[] result = RestAssured
                .given()
                .port(port)
                .when()
                .accept(JSON)
                .get("/books/author/row")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        List<BookDto> expectedList = bookMapper.toDto(Lists.newArrayList(
        new Book("10", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("100", "HarryPotter", "JK", "Rowling", "A book about teen wizards")));
        Assertions.assertThat(List.of(result)).hasSameElementsAs(expectedList);
    }

    @Test
    void givenBook_WhenRegisterBook_TheReturnBook() {
        //  GIVEN
        BookDto expectedBook = new BookDto("10", "HarryPotter", "JK", "Rowling", "A book about teen wizards");
        //  WHEN

        BookDto actualBookDto = RestAssured
                .given()
                .port(port)
                .body(expectedBook)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookDto.class);
        //  THEN
        Assertions.assertThat(actualBookDto.getIsbn()).isEqualTo(expectedBook.getIsbn());
        Assertions.assertThat(actualBookDto.getTitle()).isEqualTo(expectedBook.getTitle());
        Assertions.assertThat(actualBookDto.getAuthorFirstName()).isEqualTo(expectedBook.getAuthorFirstName());
        Assertions.assertThat(actualBookDto.getAuthorLastName()).isEqualTo(expectedBook.getAuthorLastName());
    }

}