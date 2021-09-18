package ru.otus.libraryauthenticationacl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.libraryauthenticationacl.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
