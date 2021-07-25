package ru.otus.librarymongo;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.repositories.BookRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для авторов ")
@ComponentScan({"ru.otus.librarymongo.repositories"})
@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorTest {
    @Autowired
    private BookRepository authorRepository;

    @Test
    @DisplayName("Выбор всех авторов")
    @Order(1)
    public void all() {
        List<Author> result = authorRepository.allAuthors();
        assertEquals(result.size(), 5);
    }

    @Test
    @DisplayName("Удаление автора по id")
    @Order(2)
    public void delete() {
        val result = authorRepository.allAuthors();
        int size = result.size();
        authorRepository.deleteAuthor(new Author("Lev", "Tolstoy"));
        int newSize = authorRepository.allAuthors().size();
        assertEquals(size - newSize, 1);
    }

}
