package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.models.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Comment> all();

    Comment byId(long id) throws LibraryAppException;

    long insert(Comment comment) throws LibraryAppException;

    long insert(Map<String, String> commentFields) throws LibraryAppException;

    void deleteById(long id);

    void update(Comment comment) throws LibraryAppException;

    void update(Long id, Map<String, String> commentFields) throws LibraryAppException;

    List<String> fieldsForInput();

    List<String> fieldsForUpdate();

}
