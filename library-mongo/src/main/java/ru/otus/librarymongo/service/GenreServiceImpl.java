package ru.otus.librarymongo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.librarymongo.models.Genre;
import ru.otus.librarymongo.repositories.BookRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final BookRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> all() {
        return repository.allGenres();
    }

    @Override
    @Transactional
    public void delete(Genre genre) {
        repository.deleteGenre(genre);
    }
}
