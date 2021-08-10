package ru.otus.mvclibrary.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mvclibrary.models.Genre;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
