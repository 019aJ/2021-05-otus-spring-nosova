package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.exceptions.NoSuchResultException;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.models.Genre;

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
                Collections.addAll(fields, "title", "author", "genre");
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
                Collections.addAll(fields, "text", "bookId");
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
                Collections.addAll(fields, "title", "author", "genre");
                break;
            }
            case "comment": {
                Collections.addAll(fields, "text", "bookId");
                break;
            }
            default:
                throw new InvalidInputException("Обновление возможно только для книг и комментариев");
        }
        return fields;
    }

    @Override
    public Long insert(String entityName, Map<String, String> fieldValues) throws LibraryAppException {
        entityName = formatName(entityName);
        try {
            switch (entityName) {
                case "book": {
                    return bookService.insert(BookMapper.get(fieldValues));
                }
                case "author": {
                    return authorService.insert(new Author(fieldValues.get("name"), fieldValues.get("surname")));
                }
                case "genre": {
                    return genreService.insert(new Genre(fieldValues.get("name")));
                }
                case "comment": {
                    return commentService.insert(CommentMapper.get(fieldValues));
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

    @Override
    public void comments(Long bookId) throws LibraryAppException {
        Book book = bookService.byIdEagerly(bookId);
        outputService.writeCommentsForBookResult(book.getTitle(), book.getComments());
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
            book = bookService.byIdEagerly(id);
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


    private static class CommentMapper {
        public static Comment get(Map<String, String> commentFields) throws LibraryAppException {
            String text = commentFields.get("text");
            Long bookId;
            try {
                bookId = Long.valueOf(commentFields.get("bookId"));
            } catch (Exception e) {
                throw new InvalidInputException("Id книги должно быть числом");
            }
            return Comment.builder().text(text).book(new Book(bookId, null, null, null, null)).build();
        }
    }


    private static class BookMapper {
        public static Book get(Map<String, String> bookFields) throws LibraryAppException {
            String title = bookFields.get("title");
            Long authorId;
            try {
                authorId = Long.valueOf(bookFields.get("author"));
            } catch (Exception e) {
                throw new InvalidInputException("Id автора должно быть числом");
            }
            Author author = new Author(authorId, null, null);
            Long genreId;
            try {
                genreId = StringUtils.isEmpty(bookFields.get("genre")) ? null : Long.valueOf(bookFields.get("genre"));
            } catch (Exception e) {
                throw new InvalidInputException("Id жанра должно быть числом");
            }
            Genre genre = new Genre(genreId, null);
            return new Book(title, author, genre);
        }
    }
}
