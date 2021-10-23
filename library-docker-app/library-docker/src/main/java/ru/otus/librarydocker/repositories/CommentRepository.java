package ru.otus.librarydocker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.librarydocker.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
