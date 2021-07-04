package ru.otus.libraryjpaapp.repositories;


import ru.otus.libraryjpaapp.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> all();

    Optional<Genre> byId(long id);

    Genre insert(Genre genre);

    void deleteById(long id);
}
