package ru.otus.librarymigration.batch.readers;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.otus.librarymigration.model.nosql.MongoAuthor;
import ru.otus.librarymigration.model.nosql.MongoBook;
import ru.otus.librarymigration.model.nosql.MongoComment;
import ru.otus.librarymigration.model.nosql.MongoGenre;
import ru.otus.librarymigration.model.relational.Book;

import java.util.HashMap;

@AllArgsConstructor
@Service
public class LibraryMongoReaderService {
    private MongoTemplate mongoTemplate;

    public ItemReader<MongoBook> bookReader() {
        return new MongoItemReaderBuilder
                <MongoBook>()
                .name("mongoBookItemReader")
                .template(mongoTemplate)
                .collection("Books")
                .jsonQuery("{}")
                .targetType(MongoBook.class)
                .sorts(new HashMap<>() {{
                    put("_id", Sort.Direction.DESC);
                }}).build();
    }

    public ItemReader<MongoAuthor> authorReader() {
        return new MongoDistinctItemReaderBuilder()
                .template(mongoTemplate)
                .collection("Books")
                .jsonQuery("{}")
                .fields("author")
                .entityClass(Book.class)
                .resultClass(MongoAuthor.class)
                .name("mongoAuthorItemReader")
                .sorts(new HashMap<String, Sort.Direction>() {{
                    put("surname", Sort.Direction.DESC);
                    put("name", Sort.Direction.DESC);
                }})
                .build();
    }

    public ItemReader<MongoGenre> genreReader() {
        return new MongoDistinctItemReaderBuilder().template(mongoTemplate)
                .collection("Books")
                .jsonQuery("{}")
                .fields("genre")
                .entityClass(Book.class)
                .resultClass(MongoGenre.class)
                .name("mongoGenreItemReader")
                .sorts(new HashMap<String, Sort.Direction>() {{
                    put("name", Sort.Direction.DESC);
                }})
                .build();
    }

    public ItemReader<MongoComment> commentReader() {
        return new MongoCommentItemReader(mongoTemplate);
    }
}
