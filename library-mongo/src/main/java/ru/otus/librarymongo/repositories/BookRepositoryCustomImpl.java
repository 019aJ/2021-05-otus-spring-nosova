package ru.otus.librarymongo.repositories;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Comment;
import ru.otus.librarymongo.models.Genre;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@AllArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Author> allAuthors() {
        return mongoTemplate.findDistinct(new Query(), "author", Book.class, Author.class);
    }

    @Override
    public void deleteAuthor(Author author) {
        var query = Query.query(Criteria.where("author").is(author));
        mongoTemplate.remove(query, Book.class);
    }

    @Override
    public List<Genre> allGenres() {
        return mongoTemplate.findDistinct(new Query(), "genre", Book.class, Genre.class);
    }

    @Override
    public void deleteGenre(Genre genre) {
        val query = Query.query(Criteria.where("genre").is(genre));
        val update = new Update().unset("genre");
        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public List<Book> allWithComments() {
        Aggregation aggregation = newAggregation(addFields().
                addField("b_id").withValue(ConvertOperators.ToString.toString("$_id")).build()
        );
        aggregation.getPipeline().add(lookup("Comments", "b_id", "bookId", "comments"));
        AggregationResults<Book> result = mongoTemplate.
                aggregate(aggregation, Book.class, Book.class);
        return result.getMappedResults();
    }

    @Override
    public Book bookWithComments(String title) {
        Aggregation aggregation = newAggregation(match(Criteria.where("title").is(title)));
        aggregation.getPipeline().add(addFields().
                addField("b_id").withValue(ConvertOperators.ToString.toString("$_id")).build());
        aggregation.getPipeline().add(lookup("Comments", "b_id", "bookId", "comments"));
        AggregationResults<Book> result = mongoTemplate.
                aggregate(aggregation, Book.class, Book.class);
        List<Book> mappedResults = result.getMappedResults();
        return mappedResults.size() > 0 ? mappedResults.get(0) : null;
    }

    @Override
    public String idByTitle(String title) {
        val query = Query.query(Criteria.where("title").is(title));
        query.fields().include("_id");
        Book book = mongoTemplate.findOne(query, Book.class);
        return book != null ? book.getId() : null;
    }

    @Override
    public List<Book> byAuthor(Author author) {
        val query = Query.query(Criteria.where("author").is(author));
        return mongoTemplate.find(query, Book.class, "Books");
    }

    @Override
    public List<Book> byGenre(Genre genre) {
        val query = Query.query(Criteria.where("genre").is(genre));
        return mongoTemplate.find(query, Book.class, "Books");
    }

    @Override
    public void deleteBook(String id) {
        Query query = Query.query(Criteria.where("bookId").is(id));
        mongoTemplate.remove(query, Comment.class);
        query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Book.class);
    }
}
