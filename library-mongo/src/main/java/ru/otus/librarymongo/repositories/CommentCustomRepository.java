package ru.otus.librarymongo.repositories;

import ru.otus.librarymongo.models.Comment;

public interface CommentCustomRepository {
    Comment updateText(Comment comment);
}
