package ru.otus.libraryhystrix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.libraryhystrix.models.Author;
import ru.otus.libraryhystrix.models.Genre;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    @NotBlank(message = "Title must be filled")
    private String title;
    private Author author;
    private Genre genre;
}
