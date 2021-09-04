package ru.otus.rxlibrary.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.rxlibrary.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {
}
