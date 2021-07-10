package ru.otus.libraryjpaapp.service;

import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> all();

    Comment byId(long id) throws LibraryAppException;

    long insert(Comment comment) throws LibraryAppException;

    void deleteById(long id);

    void update(Comment comment) throws LibraryAppException;

}
