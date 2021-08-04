package ru.otus.libraryjdbcapp.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjdbcapp.exceptions.InvalidInputException;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.exceptions.NoSuchResultException;
import ru.otus.libraryjdbcapp.exceptions.PkViolationException;
import ru.otus.libraryjdbcapp.models.Author;
import ru.otus.libraryjdbcapp.models.Book;
import ru.otus.libraryjdbcapp.models.Genre;
import ru.otus.libraryjdbcapp.repositories.AuthorRepository;
import ru.otus.libraryjdbcapp.repositories.GenreRepository;

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
                bookService.insert(new Book(1L, null, null, null))
        );
    }

    @Test
    @Order(1)
    @DisplayName("Вставка дубликата")
    public void insertSecondTime() throws LibraryAppException {
        Author author = new Author(1L, "Surname", "AuthorName");
        long authorId = authorRepository.insert(author);
        Genre genre = new Genre(1L, "GenreName");
        long genreId = genreRepository.insert(genre);
        long bookId = bookService.insert(new Book(1L, "BookName", author, genre));
        Assertions.assertThrows(PkViolationException.class, () ->
                bookService.insert(new Book(1L, "Duplicate", author, null))
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
