package com.bookworms.digibooky.user.api;

import com.bookworms.digibooky.user.api.dto.LibrarianDto;
import com.bookworms.digibooky.user.api.dto.MemberDto;
import com.bookworms.digibooky.user.domain.Librarian;
import com.bookworms.digibooky.user.domain.Member;
import com.bookworms.digibooky.user.service.UserMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserMapper userMapper;

    @Nested
    class MemberTests{
        @Test
        void givenMember_WhenRegisterMember_TheReturnMember() {
            //  GIVEN
            Member expectedMember = new Member("66.02.06-203.33", "Alen", "Jeremy", "alen.jeremy@awesomeness.great", "Switchfully", "3", 3454, " KarelDeGrote");
            //  WHEN

            MemberDto actualMemberDto = RestAssured
                    .given()
                    .port(port)
                    .body(expectedMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(MemberDto.class);
            //  THEN
            MemberDto expectedMemberDto = userMapper.toMemberDto(expectedMember);
            Assertions.assertThat(actualMemberDto.getId()).isNotBlank();
            Assertions.assertThat(actualMemberDto.getFirstName()).isEqualTo(expectedMemberDto.getFirstName());
            Assertions.assertThat(actualMemberDto.getLastName()).isEqualTo(expectedMemberDto.getLastName());
            Assertions.assertThat(actualMemberDto.getEmail()).isEqualTo(expectedMemberDto.getEmail());
            Assertions.assertThat(actualMemberDto.getStreetName()).isEqualTo(expectedMemberDto.getStreetName());
            Assertions.assertThat(actualMemberDto.getStreetNumber()).isEqualTo(expectedMemberDto.getStreetNumber());
            Assertions.assertThat(actualMemberDto.getPostalCode()).isEqualTo(expectedMemberDto.getPostalCode());
            Assertions.assertThat(actualMemberDto.getCity()).isEqualTo(expectedMemberDto.getCity());
        }

        @Test
        void givenImproperEmail_WhenRegisterMember_ThenHttpStatusBadRequest() {
            Member expectedMember = new Member("66.02.06-203.33", "Jeremy", "Alen", "wrongEmailAdress", "Switchfully", "3", 3454, " KarelDeGrote");
            //  WHEN
            RestAssured
                    .given()
                    .port(port)
                    .body(expectedMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void givenNotUniqueEmail_WhenRegisterMember_ThenHttpStatusBadRequest() {
            Member firstMember = new Member("66.02.06-203.33", "Jeremy", "Alen", "alen.jeremy@awesomeness.great", "Switchfully", "3", 3454, "KarelDeGrote");
            Member secondMember = new Member("88.10.05-195.44", "Herbert", "Coenen", "alen.jeremy@awesomeness.great", "Switchfully", "3", 3454, "KarelMarx");

            RestAssured
                    .given()
                    .port(port)
                    .body(firstMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members");

            //  WHEN
            RestAssured
                    .given()
                    .port(port)
                    .body(secondMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void givenNullEmail_WhenRegisterMember_ThenHttpStatusBadRequest() {
            Member expectedMember = new Member("66.02.06-203.33", "Jeremy", "Alen", null, "Switchfully", "3", 3454, " KarelDeGrote");
            //  WHEN
            RestAssured
                    .given()
                    .port(port)
                    .body(expectedMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void givenEmptyInss_WhenRegisterMember_ThenHttpStatusBadRequest() {
            Member expectedMember = new Member("", "Jeremy", "Alen", "alen.jeremy@awesomeness.great", "Switchfully", "3", 3454, " KarelDeGrote");
            //  WHEN
            RestAssured
                    .given()
                    .port(port)
                    .body(expectedMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void givenNotUniqueInss_WhenRegisterMember_ThenHttpStatusBadRequest() {
            Member firstMember = new Member("66.02.06-203.33", "Jeremy", "Alen", "alen.jeremy@awesomeness.great", "Switchfully", "3", 3454, "KarelDeGrote");
            Member secondMember = new Member("66.02.06-203.33", "Herbert", "Coenen", "alen.jeremy@digibooky.great", "Switchfully", "3", 3454, "KarelMarx");

            RestAssured
                    .given()
                    .port(port)
                    .body(firstMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members");

            //  WHEN
            RestAssured
                    .given()
                    .port(port)
                    .body(secondMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void givenEmptyLastName_WhenRegisterMember_ThenHttpStatusBadRequest() {
            Member expectedMember = new Member("66.02.06-203.33", "", "Jeremy", "alen.jeremy@awesomeness.great", "Switchfully", "3", 3454, " KarelDeGrote");
            //  WHEN
            RestAssured
                    .given()
                    .port(port)
                    .body(expectedMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void givenEmptyCity_WhenRegisterMember_ThenHttpStatusBadRequest() {
            Member expectedMember = new Member("66.02.06-203.33", "Jeremy", "Alen", "alen.jeremy@awesomeness.great", "Switchfully", "3", 3454, "");
            //  WHEN
            RestAssured
                    .given()
                    .port(port)
                    .body(expectedMember)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/members")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class LibrarianTests {


    }

    @Nested
    class AdminTests {

        @Test
        void givenAdmin_whenRegisterLibrarian_ReturnLibrarianDto() {
            //  GIVEN
            Librarian expectedLibrarian = new Librarian("The grey", "Gandalf", "Gandalf.TheGrey@TheLordOfThe.Ring");
            //  WHEN

            LibrarianDto actualLibrarianDto = RestAssured
                    .given()
                    .port(port)
                    .body(expectedLibrarian)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/librarians")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(LibrarianDto.class);

            //  THEN
            LibrarianDto expectedLibrarianDto = userMapper.toLibrarianDto(expectedLibrarian);
            Assertions.assertThat(actualLibrarianDto.getId()).isNotBlank();
            Assertions.assertThat(actualLibrarianDto.getFirstName()).isEqualTo(expectedLibrarianDto.getFirstName());
            Assertions.assertThat(actualLibrarianDto.getLastName()).isEqualTo(expectedLibrarianDto.getLastName());
            Assertions.assertThat(actualLibrarianDto.getEmail()).isEqualTo(expectedLibrarianDto.getEmail());
        }
    }
}