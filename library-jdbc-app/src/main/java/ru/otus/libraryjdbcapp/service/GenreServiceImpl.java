package ru.otus.libraryjdbcapp.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.exceptions.NoSuchResultException;
import ru.otus.libraryjdbcapp.models.Genre;
import ru.otus.libraryjdbcapp.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    @Override
    public List<Genre> all() {
        return repository.all();
    }

    @Override
    public Genre byId(long id) throws LibraryAppException {
        try {
            return repository.byId(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchResultException("Данные для id " + id + "не найдены", e);
        }
    }

    @Override
    public long insert(Genre Genre) throws LibraryAppException {
        return repository.insert(Genre);
    }

    @Override
    public long insert(Map<String, String> genreFields) throws LibraryAppException {
        return insert(new Genre(genreFields.get("name")));
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> fieldsForInput() {
        List<String> fields = new ArrayList<>();
        Collections.addAll(fields, "name");
        return fields;
    }
}
