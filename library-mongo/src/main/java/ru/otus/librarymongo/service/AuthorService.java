package ru.otus.librarymongo.service;


import ru.otus.librarymongo.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> all();

    void delete(Author author);
}
