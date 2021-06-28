package ru.otus.libraryjdbcapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.libraryjdbcapp.exceptions.InvalidInputException;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.exceptions.NoSuchResultException;
import ru.otus.libraryjdbcapp.models.Author;
import ru.otus.libraryjdbcapp.models.Book;
import ru.otus.libraryjdbcapp.models.Genre;

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
        }
        return fieldsForInput;
    }

    @Override
    public List<String> updatingFields() {
        return bookService.fieldsForUpdate();
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
            }
        } catch (Exception e) {
            throw new InvalidInputException(e);
        }
        return null;
    }

    @Override
    public void update(Long id, Map<String, String> fieldValues) throws LibraryAppException {
        bookService.update(id, fieldValues);
    }

    @Override
    public void deleteById(Long id) throws InvalidInputException {
        bookService.deleteById(id);
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
}
