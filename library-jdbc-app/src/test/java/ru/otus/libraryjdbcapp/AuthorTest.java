package ru.otus.libraryjdbcapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Author;
import ru.otus.libraryjdbcapp.repositories.AuthorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для авторов ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @Order(1)
    @DisplayName("Вставка автора с Id")
    public void insert() throws LibraryAppException {
        long id = authorRepository.insert(new Author(1L, "Surname", "Name"));
        Author result = authorRepository.byId(id);
        assertEquals(result.getName(), "Name");
        assertEquals(result.getSurname(), "Surname");
    }

    @Test
    @Order(2)
    @DisplayName("Вставка автора без Id")
    public void insertNoId() throws LibraryAppException {
        long id = authorRepository.insert(new Author("Surname2", "Name2"));
        Author result = authorRepository.byId(id);
        assertEquals(result.getName(), "Name2");
        assertEquals(result.getSurname(), "Surname2");
    }

    @Test
    @Order(3)
    @DisplayName("Выбор всех авторов")
    public void all() {
        List<Author> result = authorRepository.all();
        assertEquals(result.size(), 2);
    }

    @Test
    @Order(4)
    @DisplayName("Удаление автора по id")
    public void delete() {
        List<Author> result = authorRepository.all();
        result.forEach(x -> authorRepository.deleteById(x.getId()));
        result = authorRepository.all();
        assertEquals(result.size(), 0);
    }
}
