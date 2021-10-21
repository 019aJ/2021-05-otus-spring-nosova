package ru.otus.librarydocker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.librarydocker.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
