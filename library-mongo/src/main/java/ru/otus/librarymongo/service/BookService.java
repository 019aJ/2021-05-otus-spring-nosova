package ru.otus.librarymongo.service;

import ru.otus.librarymongo.exceptions.LibraryAppException;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Genre;

import java.util.List;

public interface BookService {
    List<Book> all();

    List<Book> allWithComments();

    Book byId(String id) throws LibraryAppException;

    Book byTitle(String title);

    Book byTitleWithComments(String title);

    String idByTitle(String title);


    String insert(Book book) throws LibraryAppException;

    void update(Book book);

    void deleteById(String id);

    List<Book> byAuthor(Author author);

    List<Book> byGenre(Genre genre);
}
