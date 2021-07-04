package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.exceptions.NoSuchResultException;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.models.Genre;

import java.util.ArrayList;
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
        entityName = formatName(entityName);
        List<String> fieldsForInput = new ArrayList<>();
        switch (entityName) {
            case "book": {
                fieldsForInput.addAll(bookService.fieldsForInput());
                break;
            }
            case "author": {
                fieldsForInput.addAll(authorService.fieldsForInput());
                break;
            }
            case "genre": {
                fieldsForInput.addAll(genreService.fieldsForInput());
                break;
            }
            case "comment": {
                fieldsForInput.addAll(commentService.fieldsForInput());
                break;
            }
        }
        return fieldsForInput;
    }

    @Override
    public List<String> updatingFields(String entityName) throws InvalidInputException {
        entityName = formatName(entityName);
        switch (entityName) {
            case "book": {
                return bookService.fieldsForUpdate();
            }
            case "comment": {
                return commentService.fieldsForUpdate();
            }
            default:
                throw new InvalidInputException("Обновление возможно только для книг и комментариев");
        }
    }

    @Override
    public Long insert(String entityName, Map<String, String> fieldValues) throws LibraryAppException {
        entityName = formatName(entityName);
        try {
            switch (entityName) {
                case "book": {
                    return bookService.insert(fieldValues);
                }
                case "author": {
                    return authorService.insert(fieldValues);
                }
                case "genre": {
                    return genreService.insert(fieldValues);
                }
                case "comment": {
                    return commentService.insert(fieldValues);
                }
            }
        } catch (Exception e) {
            throw new InvalidInputException(e);
        }
        return null;
    }

    @Override
    public void update(String entityName, Long id, Map<String, String> fieldValues) throws LibraryAppException {
        entityName = formatName(entityName);
        switch (entityName) {
            case "book": {
                bookService.update(id, fieldValues);
                break;
            }
            case "comment": {
                commentService.update(id, fieldValues);
                break;
            }
            default:
                throw new InvalidInputException("Обновление возможно только для книг и комментариев");
        }
    }

    @Override
    public void deleteById(String entityName, Long id) throws InvalidInputException {
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
    public void findById(Long id, String entityName) throws LibraryAppException {
        entityName = formatName(entityName);
        switch (entityName) {
            case "book": {
                bookById(id);
                break;
            }
            case "author": {
                authorById(id);
                break;
            }
            case "genre": {
                genreById(id);
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

    private String formatName(String entityName) {
        if (entityName == null) {
            entityName = "Book";
        }
        entityName = entityName.toLowerCase();
        return entityName;
    }

    private void bookById(Long id) throws LibraryAppException {
        Book book = null;
        try {
            book = bookService.byId(id);
        } catch (NoSuchResultException ex) {
            //Не нашли, но это ок
        }
        outputService.writeSearchResult(id, book);
    }

    private void authorById(Long id) throws LibraryAppException {
        Author author = null;
        try {
            author = authorService.byId(id);
        } catch (NoSuchResultException ex) {
            //Не нашли, но это ок
        }
        outputService.writeSearchAuthorResult(id, author);
    }

    private void genreById(Long id) throws LibraryAppException {
        Genre genre = null;
        try {
            genre = genreService.byId(id);
        } catch (NoSuchResultException ex) {
            //Не нашли, но это ок
        }
        outputService.writeSearchGenreResult(id, genre);
    }

    private void commentById(Long id) throws LibraryAppException {
        Comment comment = null;
        try {
            comment = commentService.byId(id);
        } catch (NoSuchResultException ex) {
            //Не нашли, но это ок
        }
        outputService.writeSearchCommentResult(id, comment);
    }
}
