package ru.otus.libraryauthentication.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryauthentication.models.Genre;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
