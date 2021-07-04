package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.models.Author;

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
