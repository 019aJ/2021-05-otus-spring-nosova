package ru.otus.libraryjdbcapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private Long id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
