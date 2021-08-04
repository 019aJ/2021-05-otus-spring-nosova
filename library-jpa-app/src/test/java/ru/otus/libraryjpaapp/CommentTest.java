package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.service.AuthorService;
import ru.otus.libraryjpaapp.service.BookService;
import ru.otus.libraryjpaapp.service.CommentService;
import ru.otus.libraryjpaapp.service.GenreService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для комментариев ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private BookService bookService;

    @Test
    @Order(1)
    @DisplayName("Вставка комментария")
    public void insertNoId() {
        Author author = new Author("Surname", "AuthorName");
        long authorId = authorService.insert(author);
        Genre genre = new Genre("GenreName");
        long genreId = genreService.insert(genre);
        long bookId = bookService.insert(new Book("BookName", author, genre));
        Book book = bookService.byId(bookId);
        long id = commentService.insert(Comment.builder().text("CommentName2").book(book).build());
        Comment result = commentService.byId(id);
        assertEquals(result.getText(), "CommentName2");
    }

    @Test
    @Order(2)
    @DisplayName("Выбор всех комментариев")
    public void all() {
        List<Comment> result = commentService.all();
        assertEquals(result.size(), 1);
    }

    @Test
    @Order(3)
    @DisplayName("Выбор  комментариев для книги")
    public void forBook() {
        Book book = bookService.byIdEagerly(1L);
        List<Comment> result = book.getComments();
        assertEquals(result.size(), 1);
    }

    @Test
    @Order(4)
    @DisplayName("Удаление комментария по id")
    public void delete() {
        commentService.deleteById(1L);
        List<Comment> result = commentService.all();
        assertEquals(result.size(), 0);
    }

    @Test
    @Order(5)
    @DisplayName("Удаление комментария Cascade")
    public void deleteCascade() {
        Author author = new Author("Surname2", "AuthorName2");
        long authorId = authorService.insert(author);
        Genre genre = new Genre("GenreName2");
        long genreId = genreService.insert(genre);
        long bookId = bookService.insert(new Book("BookName2", author, genre));
        Book book = bookService.byId(bookId);
        long id = commentService.insert(Comment.builder().text("CommentName2").book(book).build());
        bookService.deleteById(id);
    }

}
