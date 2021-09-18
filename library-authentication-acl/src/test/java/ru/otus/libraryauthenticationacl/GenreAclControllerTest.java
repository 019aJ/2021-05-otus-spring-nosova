package ru.otus.libraryauthenticationacl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.libraryauthenticationacl.controllers.GenreController;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GenreAclControllerTest {

    @Autowired
    private GenreController genreController;

    @Test
    @WithMockUser(
            username = "reader",
            authorities = {"READER"}
    )
    public void testNoUserAllGenres() throws Exception {
        assertThrows(AccessDeniedException.class, () -> {
            genreController.genres();
        });
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminAllGenres() throws Exception {
        genreController.genres();
    }

}
