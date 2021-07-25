package ru.otus.librarymongo.service;

import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Comment;
import ru.otus.librarymongo.models.Genre;

import java.util.List;

public interface OutputService {
    void writeAllBooks(List<Book> books);

    void writeAllAuthors(List<Author> authors);

    void writeAllGenres(List<Genre> genres);

    void writeSearchResult(String id, Book book);

    void writeSearchByTitleResult(String title, Book book);

    void writeSearchAuthorResult(String id, Author author);

    void writeSearchGenreResult(String id, Genre genre);

    void writeSearchCommentResult(String id, Comment comment);

    void writeCommentsForBookResult(String book, List<Comment> comments);
}
