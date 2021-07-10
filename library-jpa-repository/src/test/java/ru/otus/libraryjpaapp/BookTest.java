package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.repositories.AuthorRepository;
import ru.otus.libraryjpaapp.repositories.BookRepository;
import ru.otus.libraryjpaapp.repositories.CommentRepository;
import ru.otus.libraryjpaapp.repositories.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тест для книг ")
@DataJpaTest
public class BookTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Вставка книги")
    public void insert() {
        Author author = addAuthor();
        Genre genre = addGenre();
        Book book = bookRepository.save(new Book("BookName", author, genre));
        Book result = bookRepository.getById(book.getId());
        assertEquals(result.getTitle(), "BookName");
        assertEquals(result.getAuthor().getId(), author.getId());
        assertEquals(result.getGenre().getId(), genre.getId());
    }

    @Test
    @DisplayName("Выбор всех книг")
    public void all() {
        addBook();
        List<Book> result = bookRepository.findAll();
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("Выбор всех книг с комментариями")
    public void allWithComments() {
        addComment();
        List<Book> result = bookRepository.findAllEagerly();
        assertEquals(result.size(), 1);
        assertNotNull(result.get(0).getComments());
        assertEquals(result.get(0).getComments().size(), 1);

    }

    @Test
    @Order(4)
    @DisplayName("Выбор по id")
    public void byId() {
        Book book = addBook();
        Book result = bookRepository.getById(book.getId());
        assertEquals(result.getTitle(), "BookName");
    }

    @Test
    @Order(5)
    @DisplayName("Удаление книги по id")
    public void delete() {
        Book book = addBook();
        bookRepository.deleteAll();
        List<Book> result = bookRepository.findAll();
        assertEquals(result.size(), 0);
    }

    private Comment addComment() {
        Book book = addBook();
        Comment result = entityManager.persist(Comment.builder().text("CommentName2").book(book).build());
        entityManager.flush();
        entityManager.clear();
        return result;
    }

    private Book addBook() {
        return entityManager.persist(new Book("BookName", addAuthor(), addGenre()));
    }

    private Author addAuthor() {
        return entityManager.persist(new Author("Name2", "Surname2"));
    }

    private Genre addGenre() {
        return entityManager.persist(new Genre("GenreName2"));
    }
}
