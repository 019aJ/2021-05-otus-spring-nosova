package ru.otus.rxlibrary.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Books")
public class Book {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("author")
    private Author author;

    @Field("genre")
    private Genre genre;

    public Book(String title, Author author, Genre genre) {
        this.genre = genre;
        this.author = author;
        this.title = title;
    }

    public Book(String id) {
        this.id = id;
    }
}
