package ru.otus.librarymongo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Genre;
import ru.otus.librarymongo.repositories.BookRepository;
import ru.otus.librarymongo.repositories.CommentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест для книг ")
@ComponentScan({"ru.otus.librarymongo.repositories"})
@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Выбор всех книг")
    @Order(1)
    public void all() {
        List<Book> result = bookRepository.findAll();
        assertEquals(result.size(), 5);
    }

    @Test
    @DisplayName("Выбор всех книг с комментариями")
    @Order(2)
    public void allWithComments() {
        List<Book> result = bookRepository.allWithComments();
        assertEquals(result.size(), 5);
        for (Book book : result) {
            if ("Murder on the Orient Express".equals(book.getTitle())) {
                assertEquals(book.getComments().size(), 4);
            } else {
                assertEquals(book.getComments().size(), 0);
            }
        }
    }

    @Test
    @DisplayName("Вставка книги")
    @Order(3)
    public void insert() {
        Book book = bookRepository.save(new Book("BookName", new Author("author", "author"), new Genre("genre")));
        Book result = bookRepository.findById(book.getId()).orElseThrow();
        assertEquals(result.getTitle(), "BookName");
        assertEquals(result.getAuthor().getName(), "author");
        assertEquals(result.getGenre().getName(), "genre");
    }

    @Test
    @DisplayName("Выбор  комментариев для книги")
    @Order(4)
    public void comments() {
        Book book = bookRepository.bookWithComments("Murder on the Orient Express");
        assertEquals(book.getComments().size(), 4);
    }

    @Test
    @Order(5)
    @DisplayName("Выбор по title")
    public void byTitle() {
        Book result = bookRepository.findByTitle("Murder on the Orient Express");
        assertNotNull(result);
    }

    @Test
    @Order(6)
    @DisplayName("Удаление книги по id")
    public void delete() {
        Book book = bookRepository.bookWithComments("Murder on the Orient Express");
        bookRepository.deleteBook(book.getId());
        boolean result = bookRepository.findById(book.getId()).isEmpty();
        assertTrue(result);
        boolean comment = commentRepository.findById(book.getComments().get(0).getId()).isEmpty();
        assertTrue(comment);
    }


}

