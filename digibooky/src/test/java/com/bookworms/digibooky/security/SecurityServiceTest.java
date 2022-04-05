package com.bookworms.digibooky.security;

import com.bookworms.digibooky.user.domain.Admin;
import com.bookworms.digibooky.user.domain.Librarian;
import com.bookworms.digibooky.user.domain.Member;
import com.bookworms.digibooky.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static com.bookworms.digibooky.security.Feature.REGISTER_BOOK;
import static com.bookworms.digibooky.security.Feature.REGISTER_LIBRARIAN;

@SpringBootTest
@DirtiesContext
class SecurityServiceTest {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenId_WhenGetRole_ReturnRole() {
        //  GIVEN
        Admin admin = new Admin("Dumbledore", "Albus", "GryffindorAllTheWay@Hogward.en");
        Role expectedRole = Role.ADMIN;
        userRepository.saveAdmin(admin);
        //  WHEN
        Role actualRole = securityService.getRoleById(admin.getId());
        //  THEN
        Assertions.assertThat(actualRole).isEqualTo(expectedRole);
    }

    @Test
    void givenAdminIdAndRegisterLibrarianFeature_WhenValidateAuthorization_ReturnTrue() {
        //  GIVEN
        Admin admin = new Admin("Dumbledore", "Albus", "GryffindorAllTheWay@Hogward.en");
        userRepository.saveAdmin(admin);
        Feature permission = REGISTER_LIBRARIAN;
        //  WHEN
        boolean isAuthorized = securityService.validateAuthorization(admin.getId(), REGISTER_LIBRARIAN);
        //  THEN
        Assertions.assertThat(isAuthorized).isTrue();
    }

    @Test
    void givenLibrarianIdAndRegisterBookFeature_WhenValidateAuthorization_ReturnTrue() {
        //  GIVEN
        Librarian librarian = new Librarian("Dumbledore", "Albus", "GryffindorAllTheWay@Hogward.en");
        userRepository.saveLibrarian(librarian);
        Feature permission = REGISTER_BOOK;
        //  WHEN
        boolean isAuthorized = securityService.validateAuthorization(librarian.getId(), REGISTER_BOOK);
        //  THEN
        Assertions.assertThat(isAuthorized).isTrue();
    }

    @Test
    void givenLibrarianIdAndRegisterLibrarianFeature_WhenValidateAuthorization_ReturnFalse() {
        //  GIVEN
        Librarian librarian = new Librarian("Dumbledore", "Albus", "GryffindorAllTheWay@Hogward.en");
        userRepository.saveLibrarian(librarian);
        Feature permission = REGISTER_LIBRARIAN;
        //  WHEN
        boolean isAuthorized = securityService.validateAuthorization(librarian.getId(), REGISTER_LIBRARIAN);
        //  THEN
        Assertions.assertThat(isAuthorized).isFalse();
    }

    @Test
    void givenMemberIdAndRegisterBookFeature_WhenValidateAuthorization_ReturnFalse() {
        //  GIVEN
        Member member = new Member("81.08.28","Dumbledore", "Albus", "GryffindorAllTheWay@Hogward.en");
        userRepository.saveMember(member);
        Feature permission = REGISTER_BOOK;
        //  WHEN
        boolean isAuthorized = securityService.validateAuthorization(member.getId(), REGISTER_BOOK);
        //  THEN
        Assertions.assertThat(isAuthorized).isFalse();
    }
}