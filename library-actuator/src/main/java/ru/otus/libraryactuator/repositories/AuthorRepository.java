package ru.otus.libraryactuator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryactuator.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
