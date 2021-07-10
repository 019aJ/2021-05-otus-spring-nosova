package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.service.AuthorService;
import ru.otus.libraryjpaapp.service.BookService;
import ru.otus.libraryjpaapp.service.GenreService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для книг ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookTest {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private BookService bookRepository;

    @Test
    @DisplayName("Вставка книги")
    @Order(1)
    public void insert() {
        Author author = new Author("AuthorName", "Surname");
        long authorId = authorService.insert(author);
        Genre genre = new Genre("GenreName");
        long genreId = genreService.insert(genre);
        long bookId = bookRepository.insert(new Book("BookName", author, genre));
        Book result = bookRepository.byId(bookId);
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
        long authorId = authorService.insert(author);
        Genre genre = new Genre("GenreName2");
        long genreId = genreService.insert(genre);
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

        authorService.deleteById(1L);
        genreService.deleteById(1L);
        authorService.deleteById(2L);
        genreService.deleteById(2L);
        assertEquals(authorService.all().size(), 0);
        assertEquals(genreService.all().size(), 0);

    }
}
