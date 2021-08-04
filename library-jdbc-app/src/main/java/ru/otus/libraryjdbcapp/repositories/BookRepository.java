package ru.otus.libraryjdbcapp.repositories;

import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Book;

import java.util.List;

public interface BookRepository {
    List<Book> all();

    Book byId(long id);

    long insert(Book Book) throws LibraryAppException;

    void update(Book Book);

    void deleteById(long id);
}
