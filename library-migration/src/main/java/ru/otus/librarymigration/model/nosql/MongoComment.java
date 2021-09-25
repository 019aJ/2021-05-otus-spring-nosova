package ru.otus.librarymigration.model.nosql;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Comments")
public class MongoComment {
    @Id
    private String id;

    @Field("text")
    private String text;

    @Field("book")
    private List<MongoBook> book;

    @Field("bookId")
    private String bookId;
}
