package com.bookworms.digibooky.security;

import com.bookworms.digibooky.user.domain.Admin;
import com.bookworms.digibooky.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

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
}