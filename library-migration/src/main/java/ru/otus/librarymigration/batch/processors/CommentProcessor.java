package ru.otus.librarymigration.batch.processors;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.librarymigration.model.nosql.MongoBook;
import ru.otus.librarymigration.model.nosql.MongoComment;
import ru.otus.librarymigration.model.relational.Author;
import ru.otus.librarymigration.model.relational.Book;
import ru.otus.librarymigration.model.relational.Comment;
import ru.otus.librarymigration.model.relational.Genre;

@StepScope
@Component
public class CommentProcessor implements ItemProcessor<MongoComment, Comment> {
    @Override
    public Comment process(MongoComment sourceComment) throws Exception {
        Comment migratedComment = new Comment();
        MongoBook sourceBook = sourceComment.getBook().get(0);
        Book migratedBook = new Book();
        migratedBook.setTitle(sourceBook.getTitle());
        migratedBook.setAuthor(new Author(sourceBook.getAuthor().getName(), sourceBook.getAuthor().getSurname()));
        migratedBook.setGenre(new Genre(sourceBook.getGenre().getName()));
        migratedBook.setTitle(sourceBook.getTitle());
        migratedComment.setText(sourceComment.getText());
        migratedComment.setBook(migratedBook);
        return migratedComment;
    }
}
