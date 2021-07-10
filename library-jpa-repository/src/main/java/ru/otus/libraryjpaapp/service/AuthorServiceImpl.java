package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.repositories.AuthorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> all() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author byId(long id) throws LibraryAppException {
        return repository.findById(id).orElseThrow(() -> new LibraryAppException("Автор не найден"));
    }

    @Override
    @Transactional
    public long insert(Author author) throws LibraryAppException {
        try {
            return repository.save(author).getId();
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать автора", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
