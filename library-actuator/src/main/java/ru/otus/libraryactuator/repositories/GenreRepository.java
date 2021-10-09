package ru.otus.libraryactuator.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryactuator.models.Genre;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
