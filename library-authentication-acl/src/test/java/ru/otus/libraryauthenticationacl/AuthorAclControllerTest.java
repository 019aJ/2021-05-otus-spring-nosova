package ru.otus.libraryauthenticationacl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.libraryauthenticationacl.controllers.AuthorController;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthorAclControllerTest {

    @Autowired
    private AuthorController authorController;

    @Test
    @WithMockUser(
            username = "reader",
            authorities = {"READER"}
    )
    public void testNoUserAllAuthors() throws Exception {
        assertThrows(AccessDeniedException.class, () -> {
            authorController.authors();
        });
    }

    @WithMockUser(
            username = "admin",
            password = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminAllAuthors() throws Exception {
        authorController.authors();
    }


}
