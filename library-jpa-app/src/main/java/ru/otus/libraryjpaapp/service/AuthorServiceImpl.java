package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.exceptions.NoSuchResultException;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.repositories.AuthorRepository;

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
        return repository.byId(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    public long insert(Author author) throws LibraryAppException {
        try {
            return repository.insert(author).getId();
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать автора", e);
        }
    }

    @Override
    public long insert(Map<String, String> authorFields) throws LibraryAppException {
        return insert(new Author(authorFields.get("name"), authorFields.get("surname")));
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
