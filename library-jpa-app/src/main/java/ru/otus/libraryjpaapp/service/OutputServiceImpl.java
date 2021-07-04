package ru.otus.libraryjpaapp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.models.Genre;

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
            if (book.getComments().size() == 0) {
                log.info("Пока что нет ни одного комментария ");
            } else {
                log.info("Комментарии:");
                book.getComments().forEach(c -> log.info(c.getText()));
            }
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

    @Override
    public void writeSearchCommentResult(Long id, Comment comment) {
        if (comment == null) {
            log.info("Комментарий с id = " + id + " не найден.");
        } else {
            log.info("Комментарий с id = " + comment.getId() + " из книги " + comment.getBook().getId() + ":");
            log.info(comment.getText());
        }
    }

}
