package ru.otus.librarymigration.batch.processors;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.librarymigration.model.nosql.MongoBook;
import ru.otus.librarymigration.model.relational.Author;
import ru.otus.librarymigration.model.relational.Book;
import ru.otus.librarymigration.model.relational.Genre;

@StepScope
@Component
public class BookProcessor implements ItemProcessor<MongoBook, Book> {
    @Override
    public Book process(MongoBook sourceBook) throws Exception {
        Book migratedBook = new Book();
        migratedBook.setTitle(sourceBook.getTitle());
        migratedBook.setAuthor(new Author(sourceBook.getAuthor().getName(), sourceBook.getAuthor().getSurname()));
        migratedBook.setGenre(new Genre(sourceBook.getGenre().getName()));
        return migratedBook;
    }
}
