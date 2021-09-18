package ru.otus.libraryauthenticationacl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryauthenticationacl.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
