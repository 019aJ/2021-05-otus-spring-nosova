package ru.otus.libraryjpaapp;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.repositories.AuthorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для авторов ")
@DataJpaTest
public class AuthorTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Вставка автора")
    public void create() {
        Author author = addAuthor();
        assertEquals(author.getName(), "Name2");
        assertEquals(author.getSurname(), "Surname2");
    }

    @Test
    @DisplayName("Выбор всех авторов")
    public void all() {
        addAuthor();
        List<Author> result = authorRepository.findAll();
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("Удаление автора по id")
    public void delete() {
        addAuthor();
        authorRepository.deleteAll();
        val result = authorRepository.findAll();
        assertEquals(result.size(), 0);
    }

    private Author addAuthor() {
        return authorRepository.save(new Author("Name2", "Surname2"));
    }
}
