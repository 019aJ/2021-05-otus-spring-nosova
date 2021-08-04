package ru.otus.libraryjdbcapp.service;


import ru.otus.libraryjdbcapp.models.Author;
import ru.otus.libraryjdbcapp.models.Book;
import ru.otus.libraryjdbcapp.models.Genre;

public interface OutputService {
    void writeSearchResult(Long id, Book book);

    void writeSearchAuthorResult(Long id, Author author);

    void writeSearchGenreResult(Long id, Genre genre);

}
