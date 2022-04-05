package com.bookworms.digibooky.rental.api;

import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.rental.api.dto.CreateRentalDto;
import com.bookworms.digibooky.rental.api.dto.RentalDto;
import com.bookworms.digibooky.rental.domain.Rental;
import com.bookworms.digibooky.rental.domain.RentalRepository;
import com.bookworms.digibooky.rental.service.RentalMapper;
import com.bookworms.digibooky.user.domain.Member;
import com.bookworms.digibooky.user.domain.UserRepository;
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

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RentalControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalMapper rentalmapper;

    @Test
    void givenIsARental_whenAdded_thenRentalIsShown() {
        //given
        Book book = new Book("1", "JosFons", "Jos", "Fons", "story about JosFons");
        Member member = new Member("121", "Jan", "jan@piet.com", "GenkStad");
        bookRepository.save(book);
        userRepository.saveMember(member);
        //when
        CreateRentalDto expectedRental = new CreateRentalDto(member.getId(), book.getIsbn());

        rentalRepository.addRental(rentalmapper.toRental(expectedRental));

        CreateRentalDto actualRental = RestAssured
                .given()
                .port(port)
                .body(expectedRental)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/rentals")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CreateRentalDto.class);
        //then

        Assertions.assertThat(expectedRental).isEqualTo(actualRental);
    }

    @Test
    void givenIsARentalThatIsRented_whenAdded_thenErrorMessage() {
        //given
        Book book = new Book("1", "JosFons", "Jos", "Fons", "story about JosFons");
        Member member = new Member("121", "Jan", "jan@piet.com", "GenkStad");
        bookRepository.save(book);
        userRepository.saveMember(member);
        //when
        CreateRentalDto expectedRental = new CreateRentalDto(member.getId(), book.getIsbn());
        Rental rental = rentalmapper.toRental(expectedRental);

        rental.getBook().changeRentedState();
        rentalRepository.addRental(rental);

        RestAssured
                .given()
                .port(port)
                .body(expectedRental)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/rentals")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
        //then

    }

    @Test
    void givenIsARental_whenReturned_thenRentalIsDeleted() {
        //given
        Book book = new Book("1", "JosFons", "Jos", "Fons", "story about JosFons");
        Member member = new Member("121", "Jan", "jan@piet.com", "GenkStad");
        bookRepository.save(book);
        userRepository.saveMember(member);
        CreateRentalDto createRentalDto = new CreateRentalDto(member.getId(), book.getIsbn());

        //when
        Rental rental = rentalmapper.toRental(createRentalDto);
        rentalRepository.addRental(rental);

        RentalDto expectedRental = rentalmapper.toDto(rental);

        RentalDto actualRental = RestAssured
                .given()
                .port(port)
                .body(expectedRental)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .delete("/rentals/" + rental.getRentalId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RentalDto.class);
        //then
        Assertions.assertThat(expectedRental).isEqualTo(actualRental);
    }


    @Test
    void givenIsARental_whenReturned_thenRentalIsDeletedShowsError() {
        //given
        Book book = new Book("1", "JosFons", "Jos", "Fons", "story about JosFons");
        Member member = new Member("121", "Jan", "jan@piet.com", "GenkStad");
        bookRepository.save(book);
        userRepository.saveMember(member);
        CreateRentalDto createRentalDto = new CreateRentalDto(member.getId(), book.getIsbn());

        //when
        Rental rental = rentalmapper.toRental(createRentalDto);
        rentalRepository.addRental(rental);

        RentalDto expectedRental = rentalmapper.toDto(rental);


        RestAssured
                .given()
                .port(port)
                .body(expectedRental)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .delete("/rentals/2")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenIsARentalWithBookThatIsInactive_whenAdded_thenErrorMessage() {
        //given
        Book book = new Book("1", "JosFons", "Jos", "Fons", "story about JosFons");
        Member member = new Member("121", "Jan", "jan@piet.com", "GenkStad");
        bookRepository.save(book);
        userRepository.saveMember(member);
        //when
        book.changeActiveState();
        CreateRentalDto expectedRental = new CreateRentalDto(member.getId(), book.getIsbn());
        Rental rental = rentalmapper.toRental(expectedRental);

        rentalRepository.addRental(rental);

        RestAssured
                .given()
                .port(port)
                .body(expectedRental)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/rentals")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
        //then

    }

    @Test
    void showRentalsOfMember_RentalsAreShownCorrectly() {
        //given
        Book book1 = new Book("1", "JosFons", "Jos", "Fons", "story about JosFons");
        Book book2 = new Book("2", "FonsJos", "Fons", "Jos", "story about FonsJos");
        Book book3 = new Book("3", "Stay Away", "Get", "Lost", "don't show up in test results please");
        Member member = new Member("121", "Jan", "jan@piet.com", "GenkStad");
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        userRepository.saveMember(member);
        //when
        CreateRentalDto expectedRental1 = new CreateRentalDto(member.getId(), book1.getIsbn());
        CreateRentalDto expectedRental2 = new CreateRentalDto(member.getId(), book2.getIsbn());

        rentalRepository.addRental(rentalmapper.toRental(expectedRental1));
        rentalRepository.addRental(rentalmapper.toRental(expectedRental2));

        List<Rental> expectedRentalList = Lists.newArrayList(rentalmapper.toRental(expectedRental1), rentalmapper.toRental(expectedRental2));

        RentalDto[] actualRentalList = RestAssured
                .given()
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/rentals/" + member.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RentalDto[].class);
        //then

        Assertions.assertThat(List.of(actualRentalList)).hasSameElementsAs(rentalmapper.toDto(expectedRentalList));
    }

    @Test
    void showRentalsOfNonExistingMember_ThenHttpStatusBadRequest() {
        //given
        Book book1 = new Book("1", "JosFons", "Jos", "Fons", "story about JosFons");
        Book book2 = new Book("2", "FonsJos", "Fons", "Jos", "story about FonsJos");
        Book book3 = new Book("3", "Stay Away", "Get", "Lost", "don't show up in test results please");
        Member member = new Member("121", "Jan", "jan@piet.com", "GenkStad");
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        userRepository.saveMember(member);
        //when
        CreateRentalDto expectedRental1 = new CreateRentalDto(member.getId(), book1.getIsbn());
        CreateRentalDto expectedRental2 = new CreateRentalDto(member.getId(), book2.getIsbn());

        rentalRepository.addRental(rentalmapper.toRental(expectedRental1));
        rentalRepository.addRental(rentalmapper.toRental(expectedRental2));

        RestAssured
                .given()
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/rentals/" + "notAnId")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void showRentalsThatAreOverdue_RentalsAreShownCorrectly() {
        //given
        Book book1 = new Book("1", "JosFons", "Jos", "Fons", "story about JosFons");
        Book book2 = new Book("2", "FonsJos", "Fons", "Jos", "story about FonsJos");
        Book book3 = new Book("3", "Stay Away", "Get", "Lost", "don't show up in test results please");
        Member member = new Member("121", "Jan", "jan@piet.com", "GenkStad");
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        userRepository.saveMember(member);

        CreateRentalDto expectedRental1 = new CreateRentalDto(member.getId(), book1.getIsbn());
        CreateRentalDto expectedRental2 = new CreateRentalDto(member.getId(), book2.getIsbn());
        Rental rental1 = rentalmapper.toRental(expectedRental1);

        rental1.setDueDate(LocalDate.of(2000,2,2));
        Rental rental2 = rentalmapper.toRental(expectedRental2);
        rentalRepository.addRental(rental1);
        rentalRepository.addRental(rental2);
        //when
        List<Rental> expectedRentalList = Lists.newArrayList(rental1);

        RentalDto[] actualRentalList = RestAssured
                .given()
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/rentals/overdue")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RentalDto[].class);
        //then

        Assertions.assertThat(List.of(actualRentalList)).hasSameElementsAs(rentalmapper.toDto(expectedRentalList));
    }


}