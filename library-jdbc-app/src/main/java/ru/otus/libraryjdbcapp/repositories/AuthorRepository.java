package ru.otus.libraryjdbcapp.repositories;

import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> all();

    Author byId(long id);

    long insert(Author author) throws LibraryAppException;

    void deleteById(long id);
}
