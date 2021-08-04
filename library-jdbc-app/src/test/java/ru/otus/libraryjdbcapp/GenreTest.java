package ru.otus.libraryjdbcapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Genre;
import ru.otus.libraryjdbcapp.repositories.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для жанров ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GenreTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @Order(1)
    @DisplayName("Вставка жанра с Id")
    public void insert() throws LibraryAppException {
        long id = genreRepository.insert(new Genre(1L, "GenreName"));
        Genre result = genreRepository.byId(id);
        assertEquals(result.getName(), "GenreName");
    }

    @Test
    @Order(2)
    @DisplayName("Вставка жанра без Id")
    public void insertNoId() throws LibraryAppException {
        long id = genreRepository.insert(new Genre("GenreName2"));
        Genre result = genreRepository.byId(id);
        assertEquals(result.getName(), "GenreName2");
    }

    @Test
    @Order(3)
    @DisplayName("Выбор всех жанров")
    public void all() {
        List<Genre> result = genreRepository.all();
        assertEquals(result.size(), 2);
    }

    @Test
    @Order(4)
    @DisplayName("Удаление жанра по id")
    public void delete() {
        genreRepository.deleteById(1L);
        genreRepository.deleteById(2L);
        List<Genre> result = genreRepository.all();
        assertEquals(result.size(), 0);
    }

}
