package ru.otus.librarymigration.batch.processors;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.librarymigration.model.nosql.MongoGenre;
import ru.otus.librarymigration.model.relational.Genre;

@StepScope
@Component
public class GenreProcessor implements ItemProcessor<MongoGenre, Genre> {
    @Override
    public Genre process(MongoGenre sourceGenre) throws Exception {
        Genre migratedGenre = new Genre();
        migratedGenre.setName(sourceGenre.getName());
        return migratedGenre;
    }
}
