package ru.otus.libraryjpaapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryjpaapp.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
