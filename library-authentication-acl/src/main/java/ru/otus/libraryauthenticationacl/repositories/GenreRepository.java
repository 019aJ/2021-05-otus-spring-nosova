package ru.otus.libraryauthenticationacl.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryauthenticationacl.models.Genre;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
