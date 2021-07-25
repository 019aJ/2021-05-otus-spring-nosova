package ru.otus.librarymongo.service;

import ru.otus.librarymongo.exceptions.InvalidInputException;
import ru.otus.librarymongo.exceptions.LibraryAppException;

import java.util.List;
import java.util.Map;

public interface LibraryManagingService {
    List<String> insertingFields(String entityName);

    List<String> updatingFields(String entityName) throws InvalidInputException;

    String insert(String entityName, Map<String, String> fieldValues) throws LibraryAppException;

    void update(String entityName, String id, Map<String, String> fieldValues) throws LibraryAppException;

    void deleteById(String entityName, String id) throws LibraryAppException;

    void deleteAuthor(String name, String surname);

    void deleteGenre(String name);

    void findById(String id, String entityName) throws LibraryAppException;

    void comments(String bookId) throws LibraryAppException;

    void books();

    void authors();

    void genres();

    void bookByTitle(String title);

    void booksByAuthor(String name, String surname);

    void booksByGenre(String name);
}
