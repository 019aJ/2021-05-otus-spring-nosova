package ru.otus.libraryjdbcapp.repositories;

import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> all();

    Genre byId(long id);

    long insert(Genre Genre) throws LibraryAppException;

    void deleteById(long id);
}
