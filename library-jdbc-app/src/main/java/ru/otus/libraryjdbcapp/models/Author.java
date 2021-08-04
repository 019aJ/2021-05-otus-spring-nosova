package ru.otus.libraryjdbcapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Author {
    private Long id;
    private String surname;
    private String name;

    public Author(String surname, String name) {
        this.name = name;
        this.surname = surname;
    }
}
