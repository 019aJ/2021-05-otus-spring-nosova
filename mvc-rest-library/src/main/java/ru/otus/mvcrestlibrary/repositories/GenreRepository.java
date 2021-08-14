package ru.otus.mvcrestlibrary.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mvcrestlibrary.models.Genre;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
