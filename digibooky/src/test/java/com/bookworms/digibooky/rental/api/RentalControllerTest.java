package com.bookworms.digibooky.rental.api;

import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.rental.api.dto.CreateRentalDto;
import com.bookworms.digibooky.rental.domain.Rental;
import com.bookworms.digibooky.rental.domain.RentalRepository;
import com.bookworms.digibooky.rental.service.RentalMapper;
import com.bookworms.digibooky.user.domain.Member;
import com.bookworms.digibooky.user.domain.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

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
}