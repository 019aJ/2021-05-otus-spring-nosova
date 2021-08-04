package ru.otus.librarymongo.repositories;

import lombok.AllArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.librarymongo.models.Comment;

@AllArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Comment updateText(Comment comment) {
        val query = Query.query(Criteria.where("_id").is(new ObjectId(comment.getId())));
        val update = new Update().set("text", comment.getText());
        val updateResult = mongoTemplate.updateMulti(query, update, Comment.class);
        return comment;
    }
}
