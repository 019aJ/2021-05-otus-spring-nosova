package ru.otus.librarymongo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.librarymongo.exceptions.InvalidInputException;
import ru.otus.librarymongo.exceptions.LibraryAppException;
import ru.otus.librarymongo.exceptions.NoSuchResultException;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Comment;
import ru.otus.librarymongo.models.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class LibraryManagingServiceImpl implements LibraryManagingService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;

    private final OutputService outputService;

    @Override
    public List<String> insertingFields(String entityName) {
        List<String> fields = new ArrayList<>();
        entityName = formatName(entityName);
        switch (entityName) {
            case "book": {
                Collections.addAll(fields, "title", "authorName", "authorSurname", "genre");
                break;
            }
            case "author": {
                Collections.addAll(fields, "name", "surname");
                break;
            }
            case "genre": {
                Collections.addAll(fields, "name");
                break;
            }
            case "comment": {
                Collections.addAll(fields, "text", "bookTitle");
                break;
            }
        }
        return fields;
    }

    @Override
    public List<String> updatingFields(String entityName) throws InvalidInputException {
        List<String> fields = new ArrayList<>();
        entityName = formatName(entityName);
        switch (entityName) {
            case "book": {
                Collections.addAll(fields, "title", "authorName", "authorSurname", "genre");
                break;
            }
            case "comment": {
                Collections.addAll(fields, "text");
                break;
            }
            default:
                throw new InvalidInputException("Обновление возможно только для книг и комментариев");
        }
        return fields;
    }

    @Override
    public String insert(String entityName, Map<String, String> fieldValues) throws LibraryAppException {
        entityName = formatName(entityName);
        try {
            switch (entityName) {
                case "book": {
                    return bookService.insert(BookMapper.get(fieldValues));
                }
                case "comment": {
                    String bookId = bookService.idByTitle(fieldValues.get("bookTitle"));
                    if (StringUtils.isEmpty(bookId)) {
                        throw new InvalidInputException("Книга с названием" + fieldValues.get("bookTitle") + " не найдена");
                    }
                    fieldValues.put("bookId", bookId);
                    return commentService.insert(CommentMapper.get(fieldValues));
                }
            }
        } catch (Exception e) {
            throw new InvalidInputException(e);
        }
        return null;
    }

    @Override
    public void update(String entityName, String id, Map<String, String> fieldValues) throws LibraryAppException {
        entityName = formatName(entityName);
        switch (entityName) {
            case "book": {
                Book book = BookMapper.get(fieldValues);
                book.setId(id);
                bookService.update(book);
                break;
            }
            case "comment": {
                Comment comment = CommentMapper.get(fieldValues);
                comment.setId(id);
                commentService.update(comment);
                break;
            }
            default:
                throw new InvalidInputException("Обновление возможно только для книг и комментариев");
        }
    }

    @Override
    public void deleteById(String entityName, String id) throws InvalidInputException {
        entityName = formatName(entityName);
        switch (entityName) {
            case "book": {
                bookService.deleteById(id);
                break;
            }
            case "comment": {
                commentService.deleteById(id);
                break;
            }
            default:
                throw new InvalidInputException("Удаление возможно только для книг и комментариев");
        }
    }

    @Override
    public void deleteAuthor(String name, String surname) {
        authorService.delete(new Author(name, surname));
    }

    @Override
    public void deleteGenre(String name) {
        genreService.delete(new Genre(name));
    }

    @Override
    @Transactional(readOnly = true)
    public void findById(String id, String entityName) throws LibraryAppException {
        entityName = formatName(entityName);
        switch (entityName) {
            case "book": {
                bookById(id);
                break;
            }
            case "comment": {
                commentById(id);
                break;
            }
            default:
                bookById(id);
        }
    }

    @Override
    public void bookByTitle(String title) {
        Book book = bookService.byTitleWithComments(title);
        outputService.writeSearchByTitleResult(title, book);
    }

    @Override
    public void comments(String bookId) throws LibraryAppException {
        Book book = bookService.byId(bookId);
        outputService.writeCommentsForBookResult(book.getTitle(), book.getComments());
    }

    @Override
    public void books() {
        List<Book> books = bookService.allWithComments();
        outputService.writeAllBooks(books);
    }

    @Override
    public void authors() {
        List<Author> authors = authorService.all();
        outputService.writeAllAuthors(authors);
    }

    @Override
    public void genres() {
        List<Genre> genres = genreService.all();
        outputService.writeAllGenres(genres);
    }

    @Override
    public void booksByAuthor(String name, String surname) {
        List<Book> books = bookService.byAuthor(new Author(name, surname));
        outputService.writeAllBooks(books);
    }

    @Override
    public void booksByGenre(String name) {
        List<Book> books = bookService.byGenre(new Genre(name));
        outputService.writeAllBooks(books);
    }

    private String formatName(String entityName) {
        if (entityName == null) {
            entityName = "Book";
        }

        entityName = entityName.toLowerCase();
        return entityName;
    }

    private void bookById(String id) throws LibraryAppException {
        Book book = null;
        try {
            book = bookService.byId(id);
        } catch (NoSuchResultException ex) {
            //Не нашли, но это ок
        }
        outputService.writeSearchResult(id, book);
    }

    private void commentById(String id) throws LibraryAppException {
        Comment comment = null;
        try {
            comment = commentService.byId(id);
        } catch (NoSuchResultException ex) {
            //Не нашли, но это ок
        }
        outputService.writeSearchCommentResult(id, comment);
    }


    private static class CommentMapper {
        public static Comment get(Map<String, String> commentFields) throws LibraryAppException {
            String text = commentFields.get("text");
            String bookId = commentFields.get("bookId");
            return Comment.builder().text(text).bookId(bookId).build();
        }
    }


    private static class BookMapper {
        public static Book get(Map<String, String> bookFields) throws LibraryAppException {
            String title = bookFields.get("title");
            String authorName = bookFields.get("authorName");
            String authorSurname = bookFields.get("authorSurname");
            Author author = new Author(authorName, authorSurname);
            String genreName = bookFields.get("genre");
            Genre genre = new Genre(genreName);
            return new Book(title, author, genre);
        }
    }
}
