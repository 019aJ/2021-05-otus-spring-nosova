package ru.otus.librarymigration.model.nosql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@NoArgsConstructor
public class MongoAuthor {
    @Field("surname")
    private String surname;
    @Field("name")
    private String name;

    public MongoAuthor(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
