package ru.otus.libraryauthenticationacl.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.libraryauthenticationacl.models.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    List<Genre> findAll();

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    Genre save(Genre genre);
}
