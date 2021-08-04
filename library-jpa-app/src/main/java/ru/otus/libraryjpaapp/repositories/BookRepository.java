package ru.otus.libraryjpaapp.repositories;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> all();

    @Transactional(readOnly = true)
    List<Book> allEagerly();

    Optional<Book> byId(long id);

    @Transactional(readOnly = true)
    Optional<Book> byIdEagerly(long id);

    Book insert(Book Book);

    void update(Book Book);

    void deleteById(long id);
}
