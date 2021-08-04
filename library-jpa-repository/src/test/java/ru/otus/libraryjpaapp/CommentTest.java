package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.DisplayName;
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

@DisplayName("Тест для комментариев ")
@DataJpaTest
public class CommentTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Вставка комментария")
    public void create() {
        Comment result = addComment();
        assertEquals(result.getText(), "CommentName2");
    }

    @Test
    @DisplayName("Выбор всех комментариев")
    public void all() {
        Author author = addAuthor();
        Genre genre = addGenre();
        Book book = addBook(author, genre);
        Comment comment = commentRepository.save(Comment.builder().text("CommentName2").book(book).build());

        List<Comment> result = commentRepository.findAll();
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("Выбор  комментариев для книги")
    public void forBook() {
        Comment comment = addComment();
        comment = commentRepository.getById(comment.getId());
        Book book = bookRepository.getById(comment.getBook().getId());
        List<Comment> result = book.getComments();
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("Удаление комментария по id")
    public void delete() {
        Comment comment = addComment();
        commentRepository.deleteById(comment.getId());
        List<Comment> result = commentRepository.findAll();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("Удаление комментария Cascade")
    public void deleteCascade() {
        Comment comment = addComment();
        bookRepository.deleteById(comment.getBook().getId());
    }

    private Comment addComment() {
        Author author = addAuthor();
        Genre genre = addGenre();
        Book book = addBook(author, genre);
        Comment result = entityManager.persist(Comment.builder().text("CommentName2").book(book).build());
        entityManager.flush();
        entityManager.clear();
        return result;
    }

    private Book addBook(Author author, Genre genre) {
        return entityManager.persist(new Book("BookName", author, genre));
    }

    private Author addAuthor() {
        return entityManager.persist(new Author("Name2", "Surname2"));
    }

    private Genre addGenre() {
        return entityManager.persist(new Genre("GenreName2"));
    }

}
