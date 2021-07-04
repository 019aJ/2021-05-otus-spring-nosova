package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.exceptions.*;
import ru.otus.libraryjpaapp.models.Author;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Genre;
import ru.otus.libraryjpaapp.repositories.BookRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    @Override
    public List<Book> all() {
        return repository.all();
    }

    @Override
    public Book byId(long id) throws LibraryAppException {
        return repository.byId(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    public long insert(Book book) throws LibraryAppException {
        checkMandatory(book);
        try {
            return repository.insert(book).getId();
        } catch (DuplicateKeyException e) {
            throw new PkViolationException("Книга с заданным id уже существует", e);
        } catch (DataIntegrityViolationException e) {
            throw new FkViolationException("Неверный автор либо жанр", e);
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать книгу", e);
        }
    }

    @Override
    public long insert(Map<String, String> bookFields) throws LibraryAppException {
        Book book = Mapper.get(bookFields);
        checkMandatory(book);
        return insert(book);
    }

    @Override
    public void update(Book book) throws LibraryAppException {
        checkMandatory(book);
        repository.update(book);
    }

    @Override
    public void update(Long id, Map<String, String> bookFields) throws LibraryAppException {
        Book book = Mapper.get(bookFields);
        book.setId(id);
        update(book);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> fieldsForInput() {
        List<String> fields = new ArrayList<>();
        Collections.addAll(fields, "title", "author", "genre");
        return fields;
    }

    @Override
    public List<String> fieldsForUpdate() {
        List<String> fields = new ArrayList<>();
        Collections.addAll(fields, "title", "author", "genre");
        return fields;
    }

    private void checkMandatory(Book book) throws LibraryAppException {
        if (StringUtils.isEmpty(book.getTitle())) {
            throw new InvalidInputException("Название книги должно быть заполнено");
        }
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new InvalidInputException("Автор книги должен быть заполнен");
        }
    }

    private static class Mapper {
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
