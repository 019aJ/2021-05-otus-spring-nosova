package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.service.AuthorService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для авторов ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorTest {
    @Autowired
    private AuthorService authorService;


    @Test
    @Order(1)
    @DisplayName("Вставка автора")
    public void insertNoId() {
        long id = authorService.insert(new Author("Name2", "Surname2"));
        Author result = authorService.byId(id);
        assertEquals(result.getName(), "Name2");
        assertEquals(result.getSurname(), "Surname2");
    }

    @Test
    @Order(2)
    @DisplayName("Выбор всех авторов")
    public void all() {
        List<Author> result = authorService.all();
        assertEquals(result.size(), 1);
    }

    @Test
    @Order(3)
    @DisplayName("Удаление автора по id")
    public void delete() {
        List<Author> result = authorService.all();
        result.forEach(x -> authorService.deleteById(x.getId()));
        result = authorService.all();
        assertEquals(result.size(), 0);
    }
}
