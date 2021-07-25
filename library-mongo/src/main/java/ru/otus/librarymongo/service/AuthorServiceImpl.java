package ru.otus.librarymongo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.repositories.BookRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final BookRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> all() {
        return repository.allAuthors();
    }

    @Override
    @Transactional
    public void delete(Author author) {
        repository.deleteAuthor(author);
    }
}
