package ru.otus.mvcrestlibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mvcrestlibrary.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
