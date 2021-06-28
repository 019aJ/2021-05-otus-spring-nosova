package ru.otus.libraryjdbcapp.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.exceptions.NoSuchResultException;
import ru.otus.libraryjdbcapp.models.Author;
import ru.otus.libraryjdbcapp.repositories.AuthorRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    @Override
    public List<Author> all() {
        return repository.all();
    }

    @Override
    public Author byId(long id) throws LibraryAppException {
        try {
            return repository.byId(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchResultException("Данные для id " + id + "не найдены", e);
        }
    }

    @Override
    public long insert(Author author) throws LibraryAppException {
        return repository.insert(author);
    }

    @Override
    public long insert(Map<String, String> authorFields) throws LibraryAppException {
        return insert(new Author(authorFields.get("surname"), authorFields.get("name")));
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> fieldsForInput() {
        List<String> fields = new ArrayList<>();
        Collections.addAll(fields, "name", "surname");
        return fields;
    }
}
