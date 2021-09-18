package ru.otus.libraryauthentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryauthentication.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
