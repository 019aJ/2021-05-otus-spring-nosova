package ru.otus.librarymongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.librarymongo.models.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentCustomRepository {
}
