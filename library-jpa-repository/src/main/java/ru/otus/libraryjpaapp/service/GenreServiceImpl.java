package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<Genre> all() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre byId(long id) throws LibraryAppException {
        return repository.findById(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    @Transactional
    public long insert(Genre genre) throws LibraryAppException {
        try {
            return repository.save(genre).getId();
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать жанр", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
