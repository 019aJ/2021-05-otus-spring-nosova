package ru.otus.libraryjdbcapp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.libraryjdbcapp.models.Author;
import ru.otus.libraryjdbcapp.models.Book;
import ru.otus.libraryjdbcapp.models.Genre;

@Service
@Slf4j
public class OutputServiceImpl implements OutputService {

    @Override
    public void writeSearchResult(Long id, Book book) {
        if (book == null) {
            log.info("Книга с id = " + id + " не найдена.");
        } else {
            log.info("Книга с id = " + book.getId() + ":");
            String genre = !StringUtils.isEmpty(book.getGenre().getName()) ? book.getGenre().getName() : "";
            log.info(book.getTitle() + " / " + book.getAuthor().getSurname() + " " + book.getAuthor().getName() + " / " + genre);
        }
    }

    @Override
    public void writeSearchAuthorResult(Long id, Author author) {
        if (author == null) {
            log.info("Автор с id = " + id + " не найден.");
        } else {
            log.info("Автор с id = " + author.getId() + ":");
            log.info(author.getName() + " " + author.getSurname());
        }
    }

    @Override
    public void writeSearchGenreResult(Long id, Genre genre) {
        if (genre == null) {
            log.info("Жанр с id = " + id + " не найден.");
        } else {
            log.info("Жанр с id = " + genre.getId() + ":");
            log.info(genre.getName());
        }
    }

}
