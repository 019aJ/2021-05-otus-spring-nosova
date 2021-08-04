package ru.otus.librarymongo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.librarymongo.models.Genre;
import ru.otus.librarymongo.repositories.BookRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для жанров ")
@ComponentScan({"ru.otus.librarymongo.repositories"})
@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class GenreTest {
    @Autowired
    private BookRepository genreRepository;

    @Test
    @DisplayName("Выбор всех жанров")
    @Order(1)
    public void all() {
        List<Genre> result = genreRepository.allGenres();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("Удаление жанра по id")
    @Order(2)
    public void delete() {
        int size = genreRepository.allGenres().size();
        genreRepository.deleteGenre(new Genre("Novel"));
        int newSize = genreRepository.allGenres().size();
        assertEquals(size - newSize, 1);
    }
}
