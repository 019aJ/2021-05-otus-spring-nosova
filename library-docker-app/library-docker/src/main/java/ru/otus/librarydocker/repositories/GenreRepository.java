package ru.otus.librarydocker.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.librarydocker.models.Genre;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
