package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.models.Genre;

import java.util.List;
import java.util.Map;

public interface GenreService {
    List<Genre> all();

    Genre byId(long id) throws LibraryAppException;

    long insert(Genre Genre) throws LibraryAppException;

    long insert(Map<String, String> genreFields) throws LibraryAppException;

    void deleteById(long id);

    List<String> fieldsForInput();
}
