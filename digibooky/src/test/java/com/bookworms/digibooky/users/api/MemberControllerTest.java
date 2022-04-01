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
        Member expectedMember = new Member("66.02.06-203.33", "Alen", "alen.jeremy@awesomeness.great"," KarelDeGrote");
        //  WHEN

        MemberDto actualMember = RestAssured
                .given()
                .port(port)
                .when()
                 .accept(ContentType.JSON)
                .post("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDto.class);
        //  THEN
        Assertions.assertThat(actualMember).isEqualTo(MemberMapper.toDto(expectedMember));
    }
}