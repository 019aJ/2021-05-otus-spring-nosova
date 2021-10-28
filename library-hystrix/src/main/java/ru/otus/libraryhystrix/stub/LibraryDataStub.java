package ru.otus.libraryhystrix.stub;

import org.springframework.stereotype.Component;
import ru.otus.libraryhystrix.dto.BookDTO;
import ru.otus.libraryhystrix.models.Author;
import ru.otus.libraryhystrix.models.Genre;

import java.util.Arrays;
import java.util.List;

@Component
public class LibraryDataStub {
    public Author defaultAuthor() {
        return new Author(1l, "Lev", "Tolstoy");
    }

    public List<Author> defaultAuthors() {
        return Arrays.asList(defaultAuthor());
    }

    public List<Genre> defaultGenres() {
        return Arrays.asList(defaultGenre());
    }

    public List<BookDTO> defaultBooks() {
        List<BookDTO> books = Arrays.asList(new BookDTO(1L, "War and Peace", defaultAuthor(), defaultGenre()));
        return books;
    }

    private Genre defaultGenre() {
        return new Genre(1l, "Novel");
    }
}
