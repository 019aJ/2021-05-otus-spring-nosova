package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.models.Book;

import java.util.List;

public interface BookService {
    List<Book> all();

    List<Book> allEagerly();

    Book byId(long id) throws LibraryAppException;

    Book byIdEagerly(long id) throws LibraryAppException;

    long insert(Book book) throws LibraryAppException;

    void update(Book book) throws LibraryAppException;

    void deleteById(long id);
}
