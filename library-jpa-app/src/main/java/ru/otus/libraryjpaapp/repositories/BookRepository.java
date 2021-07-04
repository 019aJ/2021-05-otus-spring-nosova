package ru.otus.libraryjpaapp.repositories;

import ru.otus.libraryjpaapp.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> all();

    Optional<Book> byId(long id);

    Book insert(Book Book);

    void update(Book Book);

    void deleteById(long id);
}
