package ru.otus.libraryjdbcapp.service;

import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;

import java.util.List;
import java.util.Map;

public interface LibraryManagingService {
    List<String> insertingFields(String entityName);

    List<String> updatingFields();

    Long insert(String entityName, Map<String, String> fieldValues) throws LibraryAppException;

    void update(Long id, Map<String, String> fieldValues) throws LibraryAppException;

    void deleteById(Long id) throws LibraryAppException;

    void findById(Long id, String entityName) throws LibraryAppException;
}
