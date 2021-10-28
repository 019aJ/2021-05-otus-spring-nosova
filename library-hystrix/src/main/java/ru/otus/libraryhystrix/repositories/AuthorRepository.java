package ru.otus.libraryhystrix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryhystrix.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
