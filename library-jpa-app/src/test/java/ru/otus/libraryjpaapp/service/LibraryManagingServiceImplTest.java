package ru.otus.libraryjpaapp.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.exceptions.NoSuchResultException;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.models.Genre;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Тест для LibraryManagingService ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LibraryManagingServiceImplTest {
    @Autowired
    private LibraryManagingService libraryManagingService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CommentService commentService;

    @Test
    @Order(1)
    @DisplayName("Вставка автора")
    public void insertAuthor() throws LibraryAppException {
        long id = libraryManagingService.insert("author", Map.of("name", "AuthorName", "surname", "AuthorSurname"));
        Author result = authorService.byId(id);
        assertEquals(result.getName(), "AuthorName");
        assertEquals(result.getSurname(), "AuthorSurname");
    }

    @Test
    @Order(2)
    @DisplayName("Вставка жанра")
    public void insertGenre() throws LibraryAppException {
        long id = libraryManagingService.insert("genre", Map.of("name", "GenreName"));
        Genre result = genreService.byId(id);
        assertEquals(result.getName(), "GenreName");
    }

    @Test
    @Order(3)
    @DisplayName("Вставка книги")
    public void insertBook() throws LibraryAppException {
        try {
            long id = libraryManagingService.insert("book", Map.of("title", "BookTitle", "author", "1", "genre", "1"));
            Book result = bookService.byId(id);
            assertEquals(result.getTitle(), "BookTitle");
            assertEquals(result.getAuthor().getId(), 1L);
            assertEquals(result.getGenre().getId(), 1L);
        } catch (InvalidInputException | NoSuchResultException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(4)
    @DisplayName("Обновление книги")
    public void updateBook() throws LibraryAppException {
        try {
            libraryManagingService.update("book", 1L, Map.of("title", "BookTitle2", "author", "1", "genre", "1"));
            Book result = bookService.byId(1L);
            assertEquals(result.getTitle(), "BookTitle2");
            assertEquals(result.getAuthor().getId(), 1L);
            assertEquals(result.getGenre().getId(), 1L);
        } catch (InvalidInputException | NoSuchResultException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(5)
    @DisplayName("Вставка комментария")
    public void insertComment() throws LibraryAppException {
        long id = libraryManagingService.insert("comment", Map.of("text", "NewComment", "bookId", "1"));
        Comment result = commentService.byId(id);
        assertEquals(result.getText(), "NewComment");
    }

    @Test
    @Order(6)
    @DisplayName("Обновление комментария")
    public void updateComment() throws LibraryAppException {
        long id = 1L;
        libraryManagingService.update("comment", 1L, Map.of("text", "NewComment2", "bookId", "1"));
        Comment result = commentService.byId(id);
        assertEquals(result.getText(), "NewComment2");
    }

    @Test
    @Order(7)
    @DisplayName("Удаление комментария")
    public void deleteComment() {
        try {
            libraryManagingService.deleteById("Comment", 1L);
            List<Comment> result = commentService.all();
            assertEquals(result.size(), 0);
        } catch (LibraryAppException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(8)
    @DisplayName("Удаление книги")
    public void deleteBook() {
        try {
            libraryManagingService.deleteById("book", 1L);
            List<Book> result = bookService.all();
            assertEquals(result.size(), 0);
        } catch (LibraryAppException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(9)
    @DisplayName("Удаление")
    public void deleteAll() {
        authorService.deleteById(1L);
        genreService.deleteById(1L);
        assertEquals(authorService.all().size(), 0);
        assertEquals(genreService.all().size(), 0);
    }
}