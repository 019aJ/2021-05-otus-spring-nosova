package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> all();

    Genre byId(long id) throws LibraryAppException;

    long insert(Genre Genre) throws LibraryAppException;

    void deleteById(long id);
}
