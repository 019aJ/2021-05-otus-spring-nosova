package ru.otus.librarymongo.service;

import ru.otus.librarymongo.exceptions.LibraryAppException;

public interface CommandProcessingService {
    void insert(String entityName) throws LibraryAppException;

    void update(String id, String entityName) throws LibraryAppException;

    void delete(String id, String entityName) throws LibraryAppException;

    void deleteAuthor(String name, String surname);

    void deleteGenre(String name);

    void read(String id, String entityName) throws LibraryAppException;

    void findByTitle(String title);

    void comments(String bookId) throws LibraryAppException;

    void books();

    void authors();

    void genres();

    void booksByAuthor(String name, String surname);

    void booksByGenre(String name);
}
