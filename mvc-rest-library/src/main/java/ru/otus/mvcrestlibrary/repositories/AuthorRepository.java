package ru.otus.mvcrestlibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mvcrestlibrary.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
