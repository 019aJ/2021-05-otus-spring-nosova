package ru.otus.libraryjpaapp.service;


import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.models.Genre;

import java.util.List;

public interface OutputService {
    void writeSearchResult(Long id, Book book);

    void writeSearchAuthorResult(Long id, Author author);

    void writeSearchGenreResult(Long id, Genre genre);

    void writeSearchCommentResult(Long id, Comment comment);

    void writeCommentsForBookResult(String book, List<Comment> comments);
}
