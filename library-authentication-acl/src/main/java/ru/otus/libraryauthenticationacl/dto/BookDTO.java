package ru.otus.libraryauthenticationacl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.libraryauthenticationacl.models.Author;
import ru.otus.libraryauthenticationacl.models.Genre;

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
