package ru.otus.libraryjpaapp.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void insertAuthor() throws LibraryAppException {
        long id = libraryManagingService.insert("author", Map.of("name", "AuthorName", "surname", "AuthorSurname"));
        Author result = authorService.byId(id);
        assertEquals(result.getName(), "AuthorName");
        assertEquals(result.getSurname(), "AuthorSurname");
    }

    @Test
    @Order(2)
    @DisplayName("Вставка жанра")
    @Transactional
    public void insertGenre() throws LibraryAppException {
        long id = libraryManagingService.insert("genre", Map.of("name", "GenreName"));
        Genre result = genreService.byId(id);
        assertEquals(result.getName(), "GenreName");
    }

    @Test
    @Order(3)
    @DisplayName("Вставка книги")
    @Transactional
    public void insertBook() throws LibraryAppException {
        try {
            Long authorId = libraryManagingService.insert("author", Map.of("name", "AuthorName", "surname", "AuthorSurname"));
            Long genreId = libraryManagingService.insert("genre", Map.of("name", "GenreName"));
            long id = libraryManagingService.insert("book", Map.of("title", "BookTitle", "author", authorId.toString(), "genre", genreId.toString()));
            Book result = bookService.byId(id);
            assertEquals(result.getTitle(), "BookTitle");
            assertEquals(result.getAuthor().getId(), authorId);
            assertEquals(result.getGenre().getId(), genreId);
        } catch (InvalidInputException | NoSuchResultException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(4)
    @DisplayName("Обновление книги")
    @Transactional
    public void updateBook() throws LibraryAppException {
        try {
            Long authorId = libraryManagingService.insert("author", Map.of("name", "AuthorName", "surname", "AuthorSurname"));
            Long genreId = libraryManagingService.insert("genre", Map.of("name", "GenreName"));
            Long id = libraryManagingService.insert("book", Map.of("title", "BookTitle", "author", authorId.toString(), "genre", genreId.toString()));

            libraryManagingService.update("book", id, Map.of("title", "BookTitle2", "author", authorId.toString(), "genre", genreId.toString()));
            Book result = bookService.byId(id);
            assertEquals(result.getTitle(), "BookTitle2");
            assertEquals(result.getAuthor().getId(), authorId);
            assertEquals(result.getGenre().getId(), genreId);
        } catch (InvalidInputException | NoSuchResultException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(5)
    @DisplayName("Вставка комментария")
    @Transactional
    public void insertComment() throws LibraryAppException {
        Long authorId = libraryManagingService.insert("author", Map.of("name", "AuthorName", "surname", "AuthorSurname"));
        Long genreId = libraryManagingService.insert("genre", Map.of("name", "GenreName"));
        Long bookId = libraryManagingService.insert("book", Map.of("title", "BookTitle", "author", authorId.toString(), "genre", genreId.toString()));

        long id = libraryManagingService.insert("comment", Map.of("text", "NewComment", "bookId", bookId.toString()));
        Comment result = commentService.byId(id);
        assertEquals(result.getText(), "NewComment");
    }

    @Test
    @Order(6)
    @DisplayName("Обновление комментария")
    @Transactional
    public void updateComment() throws LibraryAppException {
        Long authorId = libraryManagingService.insert("author", Map.of("name", "AuthorName", "surname", "AuthorSurname"));
        Long genreId = libraryManagingService.insert("genre", Map.of("name", "GenreName"));
        Long bookId = libraryManagingService.insert("book", Map.of("title", "BookTitle", "author", authorId.toString(), "genre", genreId.toString()));

        long id = libraryManagingService.insert("comment", Map.of("text", "NewComment", "bookId", bookId.toString()));

        libraryManagingService.update("comment", id, Map.of("text", "NewComment2", "bookId", bookId.toString()));
        Comment result = commentService.byId(id);
        assertEquals(result.getText(), "NewComment2");
    }

    @Test
    @Order(7)
    @DisplayName("Удаление комментария")
    @Transactional
    public void deleteComment() {
        try {
            Long authorId = libraryManagingService.insert("author", Map.of("name", "AuthorName", "surname", "AuthorSurname"));
            Long genreId = libraryManagingService.insert("genre", Map.of("name", "GenreName"));
            Long bookId = libraryManagingService.insert("book", Map.of("title", "BookTitle", "author", authorId.toString(), "genre", genreId.toString()));
            long id = libraryManagingService.insert("comment", Map.of("text", "NewComment", "bookId", bookId.toString()));
            libraryManagingService.deleteById("comment", id);
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
    @Transactional
    public void deleteBook() {
        try {
            Long authorId = libraryManagingService.insert("author", Map.of("name", "AuthorName", "surname", "AuthorSurname"));
            Long genreId = libraryManagingService.insert("genre", Map.of("name", "GenreName"));
            Long bookId = libraryManagingService.insert("book", Map.of("title", "BookTitle", "author", authorId.toString(), "genre", genreId.toString()));

            libraryManagingService.deleteById("book", bookId);
            List<Book> result = bookService.all();
            assertEquals(result.size(), 0);
        } catch (LibraryAppException e) {
            e.printStackTrace();
            fail();
        }
    }
}