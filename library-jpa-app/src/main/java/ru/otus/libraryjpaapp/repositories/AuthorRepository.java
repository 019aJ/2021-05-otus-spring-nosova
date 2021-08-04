package ru.otus.libraryjpaapp.repositories;

import ru.otus.libraryjpaapp.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> all();

    Optional<Author> byId(long id);

    Author insert(Author author);

    void deleteById(long id);
}
