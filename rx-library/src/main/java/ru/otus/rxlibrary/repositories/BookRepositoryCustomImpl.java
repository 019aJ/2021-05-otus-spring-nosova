package ru.otus.rxlibrary.repositories;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import ru.otus.rxlibrary.models.Author;
import ru.otus.rxlibrary.models.Book;
import ru.otus.rxlibrary.models.Genre;

@AllArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Author> allAuthors() {
        return mongoTemplate.findDistinct(new Query(), "author", Book.class, Author.class);
    }

    @Override
    public Flux<Genre> allGenres() {
        return mongoTemplate.findDistinct(new Query(), "genre", Book.class, Genre.class);
    }

}
