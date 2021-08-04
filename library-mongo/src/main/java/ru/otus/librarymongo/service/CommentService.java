package ru.otus.librarymongo.service;

import ru.otus.librarymongo.exceptions.LibraryAppException;
import ru.otus.librarymongo.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> all();

    Comment byId(String id) throws LibraryAppException;

    String insert(Comment comment) throws LibraryAppException;

    void deleteById(String id);

    void update(Comment comment) throws LibraryAppException;

}
