package ru.otus.rxlibrary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.rxlibrary.models.Book;
import ru.otus.rxlibrary.repositories.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookController {

    @Bean
    public RouterFunction<ServerResponse> composedBookRoutes(BookRepository repository) {
        BookHandler bookHandler = new BookHandler(repository);
        return route()
                .GET("/api/books", accept(APPLICATION_JSON),
                        bookHandler::books)
                .GET("/api/books/{bookId}", accept(APPLICATION_JSON),
                        bookHandler::book)
                .POST("/api/books", accept(APPLICATION_JSON),
                        bookHandler::save)
                .PUT("/api/books", accept(APPLICATION_JSON),
                        bookHandler::save)
                .DELETE("/api/books/{bookId}", accept(APPLICATION_JSON),
                        bookHandler::delete)
                .build();
    }

    @AllArgsConstructor
    static class BookHandler {
        private final BookRepository repository;

        Mono<ServerResponse> books(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Book.class);
        }

        Mono<ServerResponse> book(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(repository.findById(request.pathVariable("bookId")), Book.class);
        }

        Mono<ServerResponse> save(ServerRequest request) {
            Mono<Book> bookMono = request.body(toMono(Book.class));
            return bookMono.flatMap(book -> ok().contentType(APPLICATION_JSON).body(repository.save(book), Book.class));
        }

        Mono<ServerResponse> delete(ServerRequest request) {
            Mono<Book> bookMono = request.body(toMono(Book.class));
            return ok().contentType(APPLICATION_JSON).body(repository.deleteById(request.pathVariable("bookId")), Book.class);
        }
    }
}
