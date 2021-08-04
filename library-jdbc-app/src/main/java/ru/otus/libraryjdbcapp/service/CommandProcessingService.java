package ru.otus.libraryjdbcapp.service;

import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;

public interface CommandProcessingService {
    void insert(String entityName) throws LibraryAppException;

    void update(String id) throws LibraryAppException;

    void delete(String id) throws LibraryAppException;

    void read(String id, String entityName) throws LibraryAppException;
}
