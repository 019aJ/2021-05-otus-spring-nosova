package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> all();

    Author byId(long id);

    long insert(Author author) throws LibraryAppException;

    void deleteById(long id);
}
