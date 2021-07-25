package ru.otus.librarymongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.librarymongo.models.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    Book findByTitle(String title);
}
