package ru.otus.librarymigration.model.nosql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
public class MongoGenre {

    @Field("name")
    private String name;

    public MongoGenre(String name) {
        this.name = name;
    }
}
