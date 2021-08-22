package ru.otus.rxlibrary.repositories;

import reactor.core.publisher.Flux;
import ru.otus.rxlibrary.models.Author;
import ru.otus.rxlibrary.models.Genre;

public interface BookRepositoryCustom {
    Flux<Author> allAuthors();

    Flux<Genre> allGenres();
}
