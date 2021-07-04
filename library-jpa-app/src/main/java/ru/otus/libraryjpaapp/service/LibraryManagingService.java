package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;

import java.util.List;
import java.util.Map;

public interface LibraryManagingService {
    List<String> insertingFields(String entityName);

    List<String> updatingFields(String entityName) throws InvalidInputException;

    Long insert(String entityName, Map<String, String> fieldValues) throws LibraryAppException;

    void update(String entityName, Long id, Map<String, String> fieldValues) throws LibraryAppException;

    void deleteById(String entityName, Long id) throws LibraryAppException;

    void findById(Long id, String entityName) throws LibraryAppException;
}
