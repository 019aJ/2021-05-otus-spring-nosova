package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.LibraryAppException;

public interface CommandProcessingService {
    void insert(String entityName) throws LibraryAppException;

    void update(String id, String entityName) throws LibraryAppException;

    void delete(String id, String entityName) throws LibraryAppException;

    void read(String id, String entityName) throws LibraryAppException;

    void comments(String bookId) throws LibraryAppException;

}
