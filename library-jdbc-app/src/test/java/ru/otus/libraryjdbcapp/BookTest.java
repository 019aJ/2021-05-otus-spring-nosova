package ru.otus.libraryjdbcapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Author;
import ru.otus.libraryjdbcapp.models.Book;
import ru.otus.libraryjdbcapp.models.Genre;
import ru.otus.libraryjdbcapp.repositories.AuthorRepository;
import ru.otus.libraryjdbcapp.repositories.BookRepository;
import ru.otus.libraryjdbcapp.repositories.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для книг ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Вставка книги")
    @Order(1)
    public void insert() throws LibraryAppException {
        Author author = new Author(1L, "Surname", "AuthorName");
        long authorId = authorRepository.insert(author);
        Genre genre = new Genre(1L, "GenreName");
        long genreId = genreRepository.insert(genre);
        long bookId = bookRepository.insert(new Book(1L, "BookName", author, genre));
        Book result = bookRepository.byId(bookId);
        assertEquals(result.getTitle(), "BookName");
        assertEquals(result.getAuthor().getId(), authorId);
        assertEquals(result.getAuthor().getName(), "AuthorName");
        assertEquals(result.getGenre().getName(), "GenreName");
        assertEquals(result.getGenre().getId(), genreId);
    }

    @Test
    @DisplayName("Вставка книги без id")
    @Order(2)
    public void insertNoId() throws LibraryAppException {
        Author author = new Author(2L, "Surname2", "AuthorName2");
        long authorId = authorRepository.insert(author);
        Genre genre = new Genre(2L, "GenreName2");
        long genreId = genreRepository.insert(genre);
        long bookId = bookRepository.insert(new Book("BookName2", author, genre));
        Book result = bookRepository.byId(bookId);
        assertEquals(result.getTitle(), "BookName2");
        assertEquals(result.getAuthor().getId(), authorId);
        assertEquals(result.getAuthor().getName(), "AuthorName2");
        assertEquals(result.getGenre().getName(), "GenreName2");
        assertEquals(result.getGenre().getId(), genreId);
    }

    @Test
    @Order(3)
    @DisplayName("Выбор всех книг")
    public void all() {
        List<Book> result = bookRepository.all();
        assertEquals(result.size(), 2);
    }

    @Test
    @Order(4)
    @DisplayName("Выбор по id")
    public void byId() {
        Book result = bookRepository.byId(1L);
        assertEquals(result.getTitle(), "BookName");
    }

    @Test
    @Order(5)
    @DisplayName("Удаление книги по id")
    public void delete() {
        bookRepository.deleteById(1L);
        bookRepository.deleteById(2L);
        List<Book> result = bookRepository.all();
        assertEquals(result.size(), 0);

        authorRepository.deleteById(1L);
        genreRepository.deleteById(1L);
        authorRepository.deleteById(2L);
        genreRepository.deleteById(2L);
        assertEquals(authorRepository.all().size(), 0);
        assertEquals(genreRepository.all().size(), 0);

    }
}
