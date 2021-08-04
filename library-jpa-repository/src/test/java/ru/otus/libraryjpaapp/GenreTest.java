package ru.otus.libraryjpaapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.repositories.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест для жанров ")
@DataJpaTest
public class GenreTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Вставка жанра")
    public void create() {
        Genre result = addGenre();
        assertEquals(result.getName(), "GenreName2");
    }

    @Test
    @DisplayName("Апдейт жанра")
    public void update() {
        Genre result = addGenre();
        result.setName("newName");
        genreRepository.save(result);
        assertEquals(result.getName(), "newName");
    }

    @Test
    @DisplayName("Выбор всех жанров")
    public void all() {
        addGenre();
        List<Genre> result = genreRepository.findAll();
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("Удаление жанра по id")
    public void delete() {
        Genre genre = addGenre();
        genreRepository.deleteById(genre.getId());
        List<Genre> result = genreRepository.findAll();
        assertEquals(result.size(), 0);
    }

    private Genre addGenre() {
        return genreRepository.save(new Genre("GenreName2"));
    }

}
