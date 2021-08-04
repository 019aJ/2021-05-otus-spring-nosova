package ru.otus.librarymongo.service;


import ru.otus.librarymongo.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> all();

    void delete(Genre genre);
}
