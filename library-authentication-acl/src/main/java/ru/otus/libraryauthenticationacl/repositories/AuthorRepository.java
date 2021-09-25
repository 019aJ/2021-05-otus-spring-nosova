package ru.otus.libraryauthenticationacl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.libraryauthenticationacl.models.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    List<Author> findAll();

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    Author save(Author author);
}
