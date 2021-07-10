package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.service.GenreService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для жанров ")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GenreTest {
    @Autowired
    private GenreService genreService;

    @Test
    @Order(1)
    @DisplayName("Вставка жанра")
    public void insertNoId() {
        Long id = genreService.insert(new Genre("GenreName2"));
        Genre result = genreService.byId(id);
        assertEquals(result.getName(), "GenreName2");
    }

    @Test
    @Order(2)
    @DisplayName("Выбор всех жанров")
    public void all() {
        List<Genre> result = genreService.all();
        assertEquals(result.size(), 1);
    }

    @Test
    @Order(3)
    @DisplayName("Удаление жанра по id")
    public void delete() {
        genreService.deleteById(1L);
        genreService.deleteById(2L);
        List<Genre> result = genreService.all();
        assertEquals(result.size(), 0);
    }

}
