package ru.otus.librarymongo.repositories;

import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Genre;

import java.util.List;

public interface BookRepositoryCustom {
    List<Author> allAuthors();

    void deleteAuthor(Author author);

    List<Genre> allGenres();

    void deleteGenre(Genre genre);

    List<Book> allWithComments();

    Book bookWithComments(String title);

    String idByTitle(String title);

    List<Book> byAuthor(Author author);

    List<Book> byGenre(Genre genre);

    void deleteBook(String id);
}
