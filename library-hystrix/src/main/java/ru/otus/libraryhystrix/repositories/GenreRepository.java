package ru.otus.libraryhystrix.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryhystrix.models.Genre;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
