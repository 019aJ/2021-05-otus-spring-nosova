package ru.otus.libraryauthenticationacl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.libraryauthenticationacl.controllers.BookController;
import ru.otus.libraryauthenticationacl.dto.BookDTO;
import ru.otus.libraryauthenticationacl.models.Author;
import ru.otus.libraryauthenticationacl.models.Genre;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BookAclControllerTest {

    @Autowired
    private BookController bookController;

    @Test
    @WithMockUser(
            username = "reader",
            authorities = {"ROLE_READER"}
    )
    public void testNoRightsForCreate() throws Exception {
        assertThrows(AccessDeniedException.class, () -> {
            bookController.createBook(new BookDTO());
        });
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void testAuthorisedCreate() throws Exception {
        BookDTO book = new BookDTO(null, "test", new Author(1L, null, null), new Genre(1L, null));
        bookController.createBook(book);
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void testAuthorisedUpdate() throws Exception {
        BookDTO book = new BookDTO(1L, "test", new Author(1L, null, null), new Genre(1L, null));
        bookController.updateBook(book);
    }

    @Test
    @WithMockUser(
            username = "reader",
            authorities = {"ROLE_READER"}
    )
    public void testNoRightsForUpdate() throws Exception {
        assertThrows(AccessDeniedException.class, () -> {
            BookDTO book = new BookDTO(1L, "test", new Author(1L, null, null), new Genre(1L, null));
            bookController.updateBook(book);
        });
    }

    @WithMockUser(
            username = "editor",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void testAuthorisedDelete() throws Exception {
        bookController.deleteBook(2L);
    }

    @Test
    @WithMockUser(
            username = "reader",
            authorities = {"ROLE_READER"}
    )
    public void testNoRightsForDelete() throws Exception {
        assertThrows(AccessDeniedException.class, () -> {
            bookController.deleteBook(2L);
        });
    }

}
