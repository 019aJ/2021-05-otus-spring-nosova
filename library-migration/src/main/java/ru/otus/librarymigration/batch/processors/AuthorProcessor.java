package ru.otus.librarymigration.batch.processors;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.librarymigration.model.nosql.MongoAuthor;
import ru.otus.librarymigration.model.relational.Author;

@StepScope
@Component
public class AuthorProcessor implements ItemProcessor<MongoAuthor, Author> {
    @Override
    public Author process(MongoAuthor sourceAuthor) throws Exception {
        Author migratedAuthor = new Author();
        migratedAuthor.setName(sourceAuthor.getName());
        migratedAuthor.setSurname(sourceAuthor.getSurname());
        return migratedAuthor;
    }
}
