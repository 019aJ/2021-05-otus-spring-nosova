package ru.otus.rxlibrary.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@NoArgsConstructor
public class Author {
    @Field("surname")
    private String surname;
    @Field("name")
    private String name;

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
