package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.exceptions.*;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.repositories.BookRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> all() {
        return repository.all();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> allEagerly() {
        return repository.allEagerly();
    }

    @Override
    @Transactional(readOnly = true)
    public Book byId(long id) throws LibraryAppException {
        return repository.byId(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    @Transactional(readOnly = true)
    public Book byIdEagerly(long id) throws LibraryAppException {
        return repository.byIdEagerly(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    @Transactional()
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
    @Transactional()
    public void update(Book book) throws LibraryAppException {
        checkMandatory(book);
        repository.update(book);
    }

    @Override
    @Transactional()
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    private void checkMandatory(Book book) throws LibraryAppException {
        if (StringUtils.isEmpty(book.getTitle())) {
            throw new InvalidInputException("Название книги должно быть заполнено");
        }
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new InvalidInputException("Автор книги должен быть заполнен");
        }
    }
}
