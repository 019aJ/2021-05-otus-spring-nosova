package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.exceptions.NoSuchResultException;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.repositories.GenreRepository;

import java.util.List;

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
        return repository.byId(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    public long insert(Genre genre) throws LibraryAppException {
        try {
            return repository.insert(genre).getId();
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать жанр", e);
        }
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
