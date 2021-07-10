package ru.otus.libraryjpaapp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
