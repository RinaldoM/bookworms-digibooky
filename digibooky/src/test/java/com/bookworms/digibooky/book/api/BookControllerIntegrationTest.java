package com.bookworms.digibooky.book.api;

import com.bookworms.digibooky.book.api.dto.BookDto;
import com.bookworms.digibooky.book.api.dto.UpdateBookDto;
import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.book.service.BookMapper;
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
                .statusCode(HttpStatus.BAD_REQUEST.value());

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

        Assertions.assertThat(bookMapper.toBook(actualBookDto).isActive()).isTrue();
        Assertions.assertThat(bookMapper.toBook(actualBookDto).getRentedState()).isFalse();

        Assertions.assertThat(actualBookDto.getIsbn()).isEqualTo(expectedBook.getIsbn());
        Assertions.assertThat(actualBookDto.getTitle()).isEqualTo(expectedBook.getTitle());
        Assertions.assertThat(actualBookDto.getAuthorFirstName()).isEqualTo(expectedBook.getAuthorFirstName());
        Assertions.assertThat(actualBookDto.getAuthorLastName()).isEqualTo(expectedBook.getAuthorLastName());
    }

    @Test
    void givenBookWithoutIsbn_WhenRegisterBook_ThenHttpStatusBadRequest() {
        //  GIVEN
        BookDto expectedBook = new BookDto("", "HarryPotter", "JK", "Rowling", "A book about teen wizards");
        //  WHEN

        RestAssured
                .given()
                .port(port)
                .body(expectedBook)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenBookWithoutTitle_WhenRegisterBook_ThenHttpStatusBadRequest() {
        //  GIVEN
        BookDto expectedBook = new BookDto("10", "", "JK", "Rowling", "A book about teen wizards");
        //  WHEN

        RestAssured
                .given()
                .port(port)
                .body(expectedBook)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenBookWithoutAuthorLastName_WhenRegisterBook_ThenHttpStatusBadRequest() {
        //  GIVEN
        BookDto expectedBook = new BookDto("10", "HarryPotter", "JK", "", "A book about teen wizards");
        //  WHEN

        RestAssured
                .given()
                .port(port)
                .body(expectedBook)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void updateBook_BookIsUpdatedCorrectly() {

        List<Book> bookList = Lists.newArrayList(
                new Book("1", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
        bookList.forEach(book -> bookRepository.save(book));

        UpdateBookDto expectedBookDto = new UpdateBookDto( "HairyPotter", "JayKay", "Coowling", "A book about teen hairy wizards");

        BookDto updatedBookDto = RestAssured
                .given()
                .port(port)
                .body(expectedBookDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .put("/books/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto.class);

        Assertions.assertThat(updatedBookDto.getIsbn()).isEqualTo("1");
        Assertions.assertThat(updatedBookDto.getTitle()).isEqualTo(expectedBookDto.getTitle());
        Assertions.assertThat(updatedBookDto.getAuthorFirstName()).isEqualTo(expectedBookDto.getAuthorFirstName());
        Assertions.assertThat(updatedBookDto.getAuthorLastName()).isEqualTo(expectedBookDto.getAuthorLastName());
        Assertions.assertThat(updatedBookDto.getSmallSummary()).isEqualTo(expectedBookDto.getSmallSummary());
    }

    @Test
    void SoftDeleteBook_BookIsSoftDeletedCorrectly() {

        List<Book> bookList = Lists.newArrayList(
                new Book("1", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
        bookList.forEach(book -> bookRepository.save(book));

        RestAssured
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .put("/books/delete/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        Assertions.assertThat(bookRepository.getBookByIsbn("1").isActive()).isFalse();


    }

    @Test
    void SoftDeleteBookThatDoesntExist_ReturnErrorMessageHttpBadRequest() {

        List<Book> bookList = Lists.newArrayList(
                new Book("1", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
        bookList.forEach(book -> bookRepository.save(book));

        RestAssured
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .put("/books/delete/5")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());



    }
    @Test
    void SoftDeleteBook_ReturnOnlyBooksThatAreActive() {

        List<Book> bookList = Lists.newArrayList(
                new Book("1", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
        bookList.forEach(book -> bookRepository.save(book));
        Book book = bookList.get(0);
        book.changeActiveState();

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

        Book newBook = new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families");
        List<BookDto> expectedList = bookMapper.toDto(List.of(newBook));

        Assertions.assertThat(List.of(result)).hasSameElementsAs(expectedList);



    }

    @Test
    void RestoreDeletedBook_BookIsRestoredCorrectly() {

        List<Book> bookList = Lists.newArrayList(
                new Book("1", "HarryPotter", "JK", "Rowling", "A book about teen wizards"),
                new Book("2", "GameOfThrone", "GeorgeRR", "Martin", "A book about pissed off families"));
        bookList.forEach(book -> bookRepository.save(book));
        Book bookByIsbn = bookRepository.getBookByIsbn("1");
        bookByIsbn.changeActiveState();

        RestAssured
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .put("/books/restore/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        Assertions.assertThat(bookRepository.getBookByIsbn("1").isActive()).isTrue();


    }





}