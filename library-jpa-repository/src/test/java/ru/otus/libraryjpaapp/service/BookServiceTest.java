package ru.otus.libraryjpaapp.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.exceptions.FkViolationException;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для Сервиса книг ")
@SpringBootTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("Не заполнены поля")
    public void noFields() {
        Assertions.assertThrows(InvalidInputException.class, () ->
                bookService.insert(new Book(null, null, null))
        );
    }

    @Test
    @Order(1)
    @DisplayName("Вставка удаленного автора")
    public void insertSecondTime() {
        Author author = new Author(1L, "Surname", "AuthorName");
        long authorId = authorService.insert(author);
        authorService.deleteById(authorId);
        Genre genre = new Genre("GenreName");
        long genreId = genreService.insert(genre);
        Assertions.assertThrows(FkViolationException.class, () ->
                bookService.insert(new Book("BookName", author, genre))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Удаление")
    public void deleteAll() {
        Author author = new Author("Surname", "AuthorName");
        long authorId = authorService.insert(author);
        Genre genre = new Genre("GenreName2");
        long genreId = genreService.insert(genre);
        Long bookId = bookService.insert(new Book("BookName", author, genre));
        bookService.deleteById(bookId);
        List<Book> result = bookService.all();

        assertEquals(result.size(), 0);

        authorService.deleteById(authorId);
        genreService.deleteById(genreId);
        genreService.deleteById(1L);
        assertEquals(authorService.all().size(), 0);
        assertEquals(genreService.all().size(), 0);
    }

}
