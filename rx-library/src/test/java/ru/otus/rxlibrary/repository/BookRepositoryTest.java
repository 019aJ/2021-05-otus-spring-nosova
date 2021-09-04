package ru.otus.rxlibrary.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.rxlibrary.models.Author;
import ru.otus.rxlibrary.models.Book;
import ru.otus.rxlibrary.models.Genre;
import ru.otus.rxlibrary.repositories.BookRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@DisplayName("Тест репозитория для книг")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    @Order(1)
    @DisplayName("Выбор всех книг")
    public void selectAll() {
        List<String> books = Arrays.asList("War and Peace", "Pride and prejudice", "Murder on the Orient Express", "It");
        Flux<Book> bookFlux = repository.findAll();
        StepVerifier
                .create(bookFlux)
                .expectNextMatches(b -> books.contains(b.getTitle()))
                .expectNextMatches(b -> books.contains(b.getTitle()))
                .expectNextMatches(b -> books.contains(b.getTitle()))
                .expectNextMatches(b -> books.contains(b.getTitle()))
                .expectComplete()
                .verify();
    }

    @Test
    @Order(2)
    public void shouldSetIdOnSave() {
        Mono<Book> bookMono = repository.save
                (new Book("Test", new Author("author", "author"), new Genre("genre")));

        StepVerifier
                .create(bookMono)
                .assertNext(book -> {
                    assertNotNull(book.getId());
                    repository.deleteById(book.getId()).subscribe();
                })
                .expectComplete()
                .verify();
    }

}
