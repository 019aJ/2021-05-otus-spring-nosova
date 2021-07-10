package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.repositories.AuthorRepository;
import ru.otus.libraryjpaapp.repositories.BookRepository;
import ru.otus.libraryjpaapp.repositories.CommentRepository;
import ru.otus.libraryjpaapp.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для комментариев ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CommentTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    @DisplayName("Вставка комментария")
    public void insertNoId() {
        Author author = new Author("Surname", "AuthorName");
        long authorId = authorRepository.insert(author).getId();
        Genre genre = new Genre("GenreName");
        long genreId = genreRepository.insert(genre).getId();
        Book book = bookRepository.insert(new Book("BookName", author, genre));
        long bookId = book.getId();
        Comment result = commentRepository.insert(Comment.builder().text("CommentName2").book(book).build());
        assertEquals(result.getText(), "CommentName2");
    }

    @Test
    @Order(2)
    @DisplayName("Выбор всех комментариев")
    public void all() {
        List<Comment> result = commentRepository.all();
        assertEquals(result.size(), 1);
    }

    @Test
    @Order(3)
    @DisplayName("Выбор  комментариев для книги")
    public void forBook() {
        Optional<Book> book = bookRepository.byIdEagerly(1L);
        List<Comment> result = book.orElse(new Book()).getComments();
        assertEquals(result.size(), 1);
    }

    @Test
    @Order(4)
    @DisplayName("Удаление комментария по id")
    public void delete() {
        commentRepository.deleteById(1L);
        List<Comment> result = commentRepository.all();
        assertEquals(result.size(), 0);
    }

    @Test
    @Order(5)
    @DisplayName("Удаление комментария Cascade")
    public void deleteCascade() {
        Author author = new Author("Surname2", "AuthorName2");
        long authorId = authorRepository.insert(author).getId();
        Genre genre = new Genre("GenreName2");
        long genreId = genreRepository.insert(genre).getId();
        Book book = bookRepository.insert(new Book("BookName2", author, genre));
        long bookId = book.getId();
        Comment result = commentRepository.insert(Comment.builder().text("CommentName2").book(book).build());
        bookRepository.deleteById(result.getId());
    }

}
