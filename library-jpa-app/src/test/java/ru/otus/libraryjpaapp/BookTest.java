package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.repositories.AuthorRepository;
import ru.otus.libraryjpaapp.repositories.BookRepository;
import ru.otus.libraryjpaapp.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

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
    public void insert() {
        Author author = new Author("AuthorName", "Surname");
        long authorId = authorRepository.insert(author).getId();
        Genre genre = new Genre("GenreName");
        long genreId = genreRepository.insert(genre).getId();
        Book result = bookRepository.insert(new Book("BookName", author, genre));
        long bookId = result.getId();
        assertEquals(result.getTitle(), "BookName");
        assertEquals(result.getAuthor().getId(), authorId);
        assertEquals(result.getAuthor().getName(), "AuthorName");
        assertEquals(result.getGenre().getName(), "GenreName");
        assertEquals(result.getGenre().getId(), genreId);
    }

    @Test
    @DisplayName("Вставка книги еще раз")
    @Order(2)
    public void insertAgain() {
        Author author = new Author("AuthorName2", "Surname2");
        long authorId = authorRepository.insert(author).getId();
        Genre genre = new Genre("GenreName2");
        long genreId = genreRepository.insert(genre).getId();
        Book result = bookRepository.insert(new Book("BookName2", author, genre));
        long bookId = result.getId();
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
        Optional<Book> result = bookRepository.byId(1L);
        assertEquals(result.get().getTitle(), "BookName");
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
