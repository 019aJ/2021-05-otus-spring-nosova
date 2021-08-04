package ru.otus.librarymongo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
public class Genre {

    @Field("name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
