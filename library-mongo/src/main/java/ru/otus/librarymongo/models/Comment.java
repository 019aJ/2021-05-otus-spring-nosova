package ru.otus.librarymongo.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Comments")
public class Comment {
    @Id
    private String id;

    @Field("text")
    private String text;

    @Field("bookId")
    private String bookId;
}
