package ru.otus.libraryjdbcapp.service;

import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> all();

    Book byId(long id) throws LibraryAppException;

    long insert(Book book) throws LibraryAppException;

    long insert(Map<String, String> bookFields) throws LibraryAppException;

    void update(Book book) throws LibraryAppException;

    void update(Long id, Map<String, String> bookFields) throws LibraryAppException;

    void deleteById(long id);

    List<String> fieldsForInput();

    List<String> fieldsForUpdate();

}
