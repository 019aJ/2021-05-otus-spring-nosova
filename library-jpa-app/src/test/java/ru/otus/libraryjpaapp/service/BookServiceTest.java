package ru.otus.libraryjpaapp.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.exceptions.FkViolationException;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.NoSuchResultException;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.repositories.AuthorRepository;
import ru.otus.libraryjpaapp.repositories.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для Сервиса книг ")
@SpringBootTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Поиск несуществующей книги")
    public void noSuchBook() {
        Assertions.assertThrows(NoSuchResultException.class, () ->
                bookService.byId(12L)
        );
    }

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
        long authorId = authorRepository.insert(author).getId();
        authorRepository.deleteById(authorId);
        Genre genre = new Genre("GenreName");
        long genreId = genreRepository.insert(genre).getId();
        Assertions.assertThrows(FkViolationException.class, () ->
                bookService.insert(new Book("BookName", author, genre))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Удаление")
    public void deleteAll() {
        bookService.deleteById(1L);
        List<Book> result = bookService.all();
        assertEquals(result.size(), 0);

        authorRepository.deleteById(1L);
        genreRepository.deleteById(1L);
        assertEquals(authorRepository.all().size(), 0);
        assertEquals(genreRepository.all().size(), 0);
    }

}
