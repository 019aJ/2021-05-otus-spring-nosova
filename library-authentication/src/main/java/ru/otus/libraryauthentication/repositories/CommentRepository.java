package ru.otus.libraryauthentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryauthentication.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
