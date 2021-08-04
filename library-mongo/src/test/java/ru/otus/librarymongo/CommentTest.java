package ru.otus.librarymongo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Comment;
import ru.otus.librarymongo.repositories.BookRepository;
import ru.otus.librarymongo.repositories.CommentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для комментариев ")
@ComponentScan({"ru.otus.librarymongo.repositories"})
@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Выбор всех комментариев")
    @Order(1)
    public void all() {
        int size = commentRepository.findAll().size();
        assertEquals(size, 4);
    }

    @Test
    @DisplayName("Вставка комментария")
    @Order(2)
    public void create() {
        Book book = bookRepository.findByTitle("It");
        Comment result = commentRepository.insert(Comment.builder().text("test").bookId(book.getId()).build());
        Comment refresh = commentRepository.findById(result.getId()).orElseThrow();
        assertEquals(refresh.getText(), "test");
    }

    @Test
    @DisplayName("Вставка комментария")
    @Order(3)
    public void update() {
        List<Comment> all = commentRepository.findAll();
        Comment comment = all.get(0);
        comment.setText("new Text");
        commentRepository.updateText(comment);
        Comment refreshedComment = commentRepository.findById(comment.getId()).orElseThrow();
        assertEquals(refreshedComment.getText(), "new Text");
    }

    @Test
    @DisplayName("Удаление комментария по id")
    @Order(4)
    public void delete() {
        List<Comment> all = commentRepository.findAll();
        Comment comment = all.get(0);
        commentRepository.deleteById(comment.getId());
        List<Comment> result = commentRepository.findAll();
        assertEquals(all.size() - result.size(), 1);
    }
}
