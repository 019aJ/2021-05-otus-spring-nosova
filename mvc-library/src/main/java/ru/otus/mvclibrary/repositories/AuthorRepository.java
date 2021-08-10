package ru.otus.mvclibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mvclibrary.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
