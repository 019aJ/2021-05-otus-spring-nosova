package ru.otus.librarymigration.model.nosql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Books")
public class MongoBook {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("author")
    private MongoAuthor author;

    @Field("genre")
    private MongoGenre genre;

    public MongoBook(String title, MongoAuthor author, MongoGenre genre) {
        this.genre = genre;
        this.author = author;
        this.title = title;
    }

    public MongoBook(String id) {
        this.id = id;
    }
}
