package ru.otus.libraryjdbcapp.service;

import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Author;

import java.util.List;
import java.util.Map;

public interface AuthorService {
    List<Author> all();

    Author byId(long id) throws LibraryAppException;

    long insert(Author author) throws LibraryAppException;

    long insert(Map<String, String> authorFields) throws LibraryAppException;

    void deleteById(long id);

    List<String> fieldsForInput();
}
