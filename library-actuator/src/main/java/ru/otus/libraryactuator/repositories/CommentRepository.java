package ru.otus.libraryactuator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryactuator.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
