package com.bookworms.digibooky.users.api;

import com.bookworms.digibooky.users.api.dto.MemberDto;
import com.bookworms.digibooky.users.domain.Member;
import com.bookworms.digibooky.users.service.MemberMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenMember_WhenRegisterMember_TheReturnMember() {
        //  GIVEN
        Member expectedMember = new Member("66.02.06-203.33","Jeremy", "Alen", "alen.jeremy@awesomeness.great", "Switchfully", "3", 3454, " KarelDeGrote");
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
        MemberDto expectedMemberDto = MemberMapper.toDto(expectedMember);
        Assertions.assertThat(actualMemberDto.getId()).isNotBlank();
        Assertions.assertThat(actualMemberDto.getFirstName()).isEqualTo(expectedMemberDto.getFirstName());
        Assertions.assertThat(actualMemberDto.getLastName()).isEqualTo(expectedMemberDto.getLastName());
        Assertions.assertThat(actualMemberDto.getEmail()).isEqualTo(expectedMemberDto.getEmail());
        Assertions.assertThat(actualMemberDto.getStreetName()).isEqualTo(expectedMemberDto.getStreetName());
        Assertions.assertThat(actualMemberDto.getStreetNumber()).isEqualTo(expectedMemberDto.getStreetNumber());
        Assertions.assertThat(actualMemberDto.getPostalCode()).isEqualTo(expectedMemberDto.getPostalCode());
        Assertions.assertThat(actualMemberDto.getCity()).isEqualTo(expectedMemberDto.getCity());
    }
}