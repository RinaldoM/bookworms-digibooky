package com.bookworms.digibooky.security;

import com.bookworms.digibooky.user.domain.Admin;
import com.bookworms.digibooky.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

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
        Role actualRole = securityService.getRole(admin.getId());
        //  THEN
        Assertions.assertThat(actualRole).isEqualTo(expectedRole);
    }

    @Test
    void givenAdminIdAndRegisterLibrarianPermission_WhenValidateAuthorization_ReturnTrue() {
        //  GIVEN
        Admin admin = new Admin("Dumbledore", "Albus", "GryffindorAllTheWay@Hogward.en");
        Feature permission = REGISTER_LIBRARIAN;
        //  WHEN
        boolean isAuthorized = securityService.validateAuthorization(admin, REGISTER_LIBRARIAN);
        //  THEN
        Assertions.assertThat(isAuthorized).isTrue();
    }

    @Test
    void givenLibrarianIdAndRegisterLibrarianPermission_WhenValidateAuthorization_ReturnTrue() {
        //  GIVEN
        Admin admin = new Admin("Dumbledore", "Albus", "GryffindorAllTheWay@Hogward.en");
        Feature permission = REGISTER_LIBRARIAN;
        //  WHEN
        boolean isAuthorized = securityService.validateAuthorization(admin, REGISTER_LIBRARIAN);
        //  THEN
        Assertions.assertThat(isAuthorized).isTrue();
    }
}