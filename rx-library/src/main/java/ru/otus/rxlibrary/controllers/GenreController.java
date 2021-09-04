package ru.otus.rxlibrary.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.rxlibrary.models.Genre;
import ru.otus.rxlibrary.repositories.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class GenreController {
    @Bean
    public RouterFunction<ServerResponse> composedGenreRoutes(BookRepository repository) {
        return route()
                .GET("/api/genres", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(repository.allGenres(), Genre.class))
                .build();
    }
}
