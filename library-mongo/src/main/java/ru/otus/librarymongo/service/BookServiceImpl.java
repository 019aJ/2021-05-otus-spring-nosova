package ru.otus.librarymongo.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.librarymongo.exceptions.*;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Genre;
import ru.otus.librarymongo.repositories.BookRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> all() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> allWithComments() {
        return repository.allWithComments();
    }

    @Override
    @Transactional(readOnly = true)
    public Book byId(String id) throws LibraryAppException {
        return repository.findById(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    @Transactional(readOnly = true)
    public Book byTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public Book byTitleWithComments(String title) {
        return repository.bookWithComments(title);
    }

    @Override
    @Transactional(readOnly = true)
    public String idByTitle(String title) {
        return repository.idByTitle(title);
    }


    @Override
    @Transactional
    public String insert(Book book) throws LibraryAppException {
        checkMandatory(book);
        try {
            return repository.save(book).getId();
        } catch (DuplicateKeyException e) {
            throw new PkViolationException("Книга с заданным id уже существует", e);
        } catch (DataIntegrityViolationException e) {
            throw new FkViolationException("Неверный автор либо жанр", e);
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать книгу", e);
        }
    }

    @Override
    @Transactional
    public void update(Book book) {
        checkMandatory(book);
        repository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        repository.deleteBook(id);
    }

    @Override
    public List<Book> byAuthor(Author author) {
        return repository.byAuthor(author);
    }

    @Override
    public List<Book> byGenre(Genre genre) {
        return repository.byGenre(genre);
    }

    private void checkMandatory(Book book) throws LibraryAppException {
        if (StringUtils.isEmpty(book.getTitle())) {
            throw new InvalidInputException("Название книги должно быть заполнено");
        }
        if (book.getAuthor() == null || book.getAuthor().getName() == null) {
            throw new InvalidInputException("Автор книги должен быть заполнен");
        }
    }
}
