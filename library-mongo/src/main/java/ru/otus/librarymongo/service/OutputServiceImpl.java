package ru.otus.librarymongo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Comment;
import ru.otus.librarymongo.models.Genre;

import java.util.List;

@Service
@Slf4j
public class OutputServiceImpl implements OutputService {

    @Override
    public void writeAllBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            log.info("В библиотеке нет книг");
            return;
        }

        books.forEach(book -> {
            String genre = book.getGenre() != null && !StringUtils.isEmpty(book.getGenre().getName()) ? book.getGenre().getName() : "";
            log.info(book.getTitle() + " / " + book.getAuthor().getSurname() + " " + book.getAuthor().getName() + " / " + genre);
            if (book.getComments() != null && book.getComments().size() > 0) {
                log.info("Комментарии:");
                book.getComments().forEach(c -> log.info(c.getText()));
            }
        });

    }

    @Override
    public void writeAllAuthors(List<Author> authors) {
        if (authors == null || authors.isEmpty()) {
            log.info("В библиотеке нет авторов");
            return;
        }
        log.info("Авторы:");
        authors.forEach(author -> log.info(author.getName() + " " + author.getSurname()));
    }

    @Override
    public void writeAllGenres(List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            log.info("В библиотеке нет жанров");
            return;
        }
        log.info("Жанры:");
        genres.forEach(genre -> log.info(genre.getName()));
    }

    @Override
    public void writeSearchResult(String id, Book book) {
        printBook(book, "Книга с id = " + id + " не найдена.", "Книга с id = " + id + ":");
    }

    @Override
    public void writeSearchByTitleResult(String title, Book book) {
        printBook(book, "Книга " + title + " не найдена.", "Книга " + title + ":");

    }

    @Override
    public void writeSearchAuthorResult(String id, Author author) {
        if (author == null) {
            log.info("Автор с id = " + id + " не найден.");
        } else {
            log.info(author.getName() + " " + author.getSurname());
        }
    }

    @Override
    public void writeSearchGenreResult(String id, Genre genre) {
        if (genre == null) {
            log.info("Жанр с id = " + id + " не найден.");
        } else {
//            log.info("Жанр с id = " + genre.getId() + ":");
            log.info(genre.getName());
        }
    }

    @Override
    public void writeSearchCommentResult(String id, Comment comment) {
        if (comment == null) {
            log.info("Комментарий с id = " + id + " не найден.");
        } else {
            log.info("Комментарий с id = " + comment.getId() + " из книги " + /*comment.getBook().getId() +*/ ":");
            log.info(comment.getText());
        }
    }

    @Override
    public void writeCommentsForBookResult(String book, List<Comment> comments) {
        if (CollectionUtils.isEmpty(comments)) {
            log.info("Комментарии для книги " + book + " не найдены.");
        } else {
            log.info("Комментарий  для книги " + book + ":");
            comments.forEach(comment -> log.info(comment.getText()));
        }
    }

    private void printBook(Book book, String notFoundText, String foundText) {
        if (book == null) {
            log.info(notFoundText);
        } else {
            log.info(foundText);
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

}
