package ru.otus.libraryjpaapp.repositories;

import ru.otus.libraryjpaapp.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> all();

    Optional<Comment> byId(long id);

    Comment insert(Comment comment);

    void update(Comment comment);

    void deleteById(long id);
}
